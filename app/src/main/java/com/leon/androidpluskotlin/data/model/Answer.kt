package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVObject

@AVClassName("Answer")
class Answer : AVObject() {

    companion object {

        val CONTENT = "content"

        val USER = "user"

        val USER_NAME = "user.username"

        val USER_AVATAR = "user.avatar"

        val QUESTION = "question"

        val LIKE_COUNT = "like_count"

        val COMMENT_COUNT = "comment_count"

        val QUESTION_TITLE = "question.title"
    }

    var question: Question
        get() = getAVObject(QUESTION)
        set(question) = put(QUESTION, question)

    var user: User
        get() = getAVUser(USER, User::class.java)
        set(user) = put(USER, user)

    val content: String
        get() = getString(CONTENT)

    val likeCount: Int
        get() = getInt(LIKE_COUNT)

    val commentCount: Int
        get() = getInt(COMMENT_COUNT)

    fun setAnswer(answer: String) {
        put(CONTENT, answer)
    }

    fun addLikeCount() {
        increment(LIKE_COUNT)
    }

    fun addCommentCount() {
        increment(COMMENT_COUNT)
    }

    fun minusLikeCount() {
        increment(LIKE_COUNT, -1)
        saveInBackground()
    }

}
