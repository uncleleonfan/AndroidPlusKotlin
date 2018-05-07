package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVObject

@AVClassName("Question")
class Question : AVObject() {

    var title: String
        get() = getString(TITLE)
        set(title) = put(TITLE, title)

    var description: String
        get() = getString(DESC)
        set(desc) = put(DESC, desc)

    val answerCount: Int
        get() = getInt(ANSWER_COUNT)

    val favourCount: Int
        get() = getInt(FAVOUR_COUNT)

    var user: User
        get() = getAVUser(USER, User::class.java)
        set(dependent) = put(USER, dependent)


    fun addAnswerCount() {
        increment(ANSWER_COUNT)
        saveInBackground()
    }

    fun addFavourCount() {
        increment(FAVOUR_COUNT)
    }

    fun minusFavourCount() {
        increment(FAVOUR_COUNT, -1)
        saveInBackground()
    }

    companion object {

        val FAVOUR_COUNT = "favour_count"

        val ANSWER_COUNT = "answer_count"

        val TITLE = "title"

        val DESC = "desc"

        val USER = "user"

        val USER_NAME = "user.username"

        val USER_AVATAR = "user.avatar"
    }

}
