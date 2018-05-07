package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.*
import java.util.*

@AVClassName("UserAnswerFavourMap")
class UserAnswerFavourMap : AVObject() {

    fun setUser(user: User) {
        put(USER, user)
    }

    fun setAnswer(answer: Answer) {
        put(ANSWER, answer)
    }

    companion object {

        val USER = "user"
        val ANSWER = "answer"
        val ANSWER_QUESTION = ANSWER + "." + Answer.QUESTION
        val ANSWER_USER = ANSWER + "." + Answer.USER
        val ANSWER_USER_USERNAME = ANSWER + "." + Answer.USER_NAME
        val ANSWER_QUESTION_TITLE = ANSWER + "." + Answer.QUESTION_TITLE
        val ANSWER_CONTENT = ANSWER + "." + Answer.CONTENT
        val ANSWER_LIKE_COUNT = ANSWER + "." + Answer.LIKE_COUNT
        val ANSWER_COMMENT_COUNT = ANSWER + "." + Answer.COMMENT_COUNT

        fun buildFavourMap(user: User, answer: Answer) {
            user.addLikedAnswer(answer.objectId)
            answer.addLikeCount()
            val userAnswerFavourMap = UserAnswerFavourMap()
            userAnswerFavourMap.setUser(user)
            userAnswerFavourMap.setAnswer(answer)
            userAnswerFavourMap.saveInBackground()
        }

        fun breakFavourMap(user: User, answer: Answer) {
            user.removeLikedAnswer(answer.objectId)
            answer.minusLikeCount()
            val userQuery = AVQuery.getQuery(UserAnswerFavourMap::class.java)
            userQuery.whereEqualTo(USER, user)
            val answerQuery = AVQuery.getQuery(UserAnswerFavourMap::class.java)
            answerQuery.whereEqualTo(ANSWER, answer)
            val query = AVQuery.and(Arrays.asList(userQuery, answerQuery))
            query.deleteAllInBackground(object : DeleteCallback() {
                override fun done(e: AVException) {

                }
            })

        }
    }
}
