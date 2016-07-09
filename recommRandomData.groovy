/*Recommendation System By Bikash Sapkota*/
/*In case recomSystemMovielens.groovy doesnt work*/
//Datasets for recommendation system
def critics=['Lisa Rose': ['Lady in the Water': 2.5, 'Snakes on a Plane': 3.5,
'Just My Luck': 3.0, 'Superman Returns': 3.5, 'You, Me and Dupree': 2.5,
'The Night Listener': 3.0],
'Gene Seymour': ['Lady in the Water': 3.0, 'Snakes on a Plane': 3.5,
'Just My Luck': 1.5, 'Superman Returns': 5.0, 'The Night Listener': 3.0,
'You, Me and Dupree': 3.5],
'Michael Phillips': ['Lady in the Water': 2.5, 'Snakes on a Plane': 3.0,
'Superman Returns': 3.5, 'The Night Listener': 4.0],
'Claudia Puig': ['Snakes on a Plane': 3.5, 'Just My Luck': 3.0,
'The Night Listener': 4.5, 'Superman Returns': 4.0,
'You, Me and Dupree': 2.5],
'Mick LaSalle': ['Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,
'Just My Luck': 2.0, 'Superman Returns': 3.0, 'The Night Listener': 3.0,
'You, Me and Dupree': 2.0],
'Jack Matthews': ['Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,
'The Night Listener': 3.0, 'Superman Returns': 5.0, 'You, Me and Dupree': 3.5],
'Toby': ['Snakes on a Plane':4.5,'You, Me and Dupree':1.0,'Superman Returns':4.0]]

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
    return matrix.max{it.value}.key
}

//returns recommendation to the user comparing the movies rated by most similar user
def getRecommendation(user,critics){
    simUser = getSimilarUser(user,critics)
    recommended = [:]
    critics[simUser].each{
       if(critics[user][it.key]==null){
           recommended[it.key]=it.value
       }     
    }
    recommended = recommended.sort{ b, a -> a.value <=> b.value }
    return recommended
 } 

 //here we ask for recommendation for Michael Phillips using dataset Critics
def recommendation  = getRecommendation('Michael Phillips',critics)
 
 println "The recommended Movie for Michael are in order: "
 recommendation.each{
     print it.key+", "
}
