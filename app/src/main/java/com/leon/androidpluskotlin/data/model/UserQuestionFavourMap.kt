package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.*
import java.util.*

@AVClassName("UserQuestionFavourMap")
class UserQuestionFavourMap : AVObject() {

    fun setUser(user: User) {
        put(USER, user)
    }

    fun setQuestion(question: Question) {
        put(QUESTION, question)
    }

    companion object {

        val USER = "user"
        val QUESTION = "question"

        fun buildFavourMap(user: User, question: Question) {
            question.addFavourCount()
            user.addFavourQuestion(question.objectId)
            val userQuestionFavourMap = UserQuestionFavourMap()
            userQuestionFavourMap.put(USER, user)
            userQuestionFavourMap.put(QUESTION, question)
            userQuestionFavourMap.saveInBackground()
        }

        fun breakFavourMap(user: User, question: Question) {
            question.minusFavourCount()
            user.removeFavouredQuestion(question.objectId)
            val userQuery = AVQuery.getQuery(UserQuestionFavourMap::class.java)
            userQuery.whereEqualTo(USER, user)
            val questionQuery = AVQuery.getQuery(UserQuestionFavourMap::class.java)
            questionQuery.whereEqualTo(QUESTION, question)
            val query = AVQuery.and(Arrays.asList(userQuery, questionQuery))
            query.deleteAllInBackground(object : DeleteCallback() {
                override fun done(e: AVException?) {
                    if (e == null) {
                    }
                }
            })
        }
    }

}

