package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVObject


@AVClassName("Comment")
class Comment : AVObject() {

    var content: String
        get() = getString(CONTENT)
        set(comment) = put(CONTENT, comment)

    var answer: Answer
        get() = getAVObject(ANSWER)
        set(answer) = put(ANSWER, answer)

    var user: User
        get() = getAVUser(USER, User::class.java)
        set(user) = put(USER, user)

    companion object {

        val CONTENT = "content"
        val ANSWER = "answer"
        val USER = "user"

        val USER_NAME = "user.username"
        val USER_AVATAR = "user.avatar"
    }
}
