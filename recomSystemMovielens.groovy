def movieFile = new File('/home/bikash/groovy/movielens/movies.txt').getText();
def ratingFile = new File('/home/bikash/groovy/movielens/ratings.csv').getText();
ConfigObject userMovie = new ConfigObject()  //multidimensional map
def movie = [:]


movieFile.eachLine{
    (id,name,type) = it.tokenize(',')
    movie[id] = name
}

ratingFile.eachLine{
    (user, movieid, rating,ts) = it.tokenize(',"')
    userMovie[user][movie[movieid]] = rating as float
}

import java.lang.*;
//calculates euclidian distance between two user
def euclidian(user1,user2, critics){
    si = []
    sumofsquare = 0
    critics[user1].each{
        it1 = it
        critics[user2].each{
            if(it.key == it1.key){
                sumofsquare = sumofsquare + Math.pow(it.value-it1.value,2)
            }
        }
    }
    return 1/(1+sumofsquare)
}

//returns most similar person with given user
def getSimilarUser(user,critics){

    def matrix = [:]
    critics.each{
        if(user!=it.key)
            matrix[it.key] = euclidian(user,it.key,critics)

    }
    return matrix.min{it.value}.key
}

//returns recommendation to the user comparing the movies rated by most similar user
def getRecommendation(user,critics){
    simUser = getSimilarUser(user,critics)
    recommended = [:]

    critics[simUser].each{
        if(critics[user][it.key]==[:]){
            recommended[it.key]=it.value
        }
    }
    recommended = recommended.sort{ b, a -> a.value <=> b.value }
    return recommended
}

//here we ask for recommendation for user of id 21 are Phillips using dataset Critics
def recommendation  = getRecommendation('21',userMovie)

println "The recommended Movie for 21 are in order: "
def temp = 0
recommendation.each{
    println it.key+", "
    temp = temp+1
    if(temp>20)
        return false
}

