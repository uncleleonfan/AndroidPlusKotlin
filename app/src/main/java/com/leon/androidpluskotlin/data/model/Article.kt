package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVObject

@AVClassName("Article")
class Article : AVObject() {

    var title: String
        get() = getString(TITLE)
        set(title) = put(TITLE, title)

    val des: String
        get() = getString(DESC)

    var url: String
        get() = getString(URL)
        set(url) = put(URL, url)

    var user: User
        get() = getAVUser(USER, User::class.java)
        set(user) = put(USER, user)

    val favourCount: Int
        get() = getInt(FAVOUR_COUNT)

    fun setDesc(desc: String) {
        put(DESC, desc)
    }

    fun setTag(tag: Int) {
        put(TAG, tag)
    }

    fun increaseFavourCount() {
        increment(FAVOUR_COUNT)
    }

    fun decreaseFavourCount() {
        increment(FAVOUR_COUNT, -1)
        saveInBackground()
    }

    companion object {

        val TAG_HOT = 0
        val TAG_THINKING = 1
        val TAG_PROJECT = 2
        val TAG_SDK = 3
        val TAG_KOTLIN = 4
        val TAG_CUSTOM_VIEW = 5
        val TAG_THIRD_PARTY = 6
        val TAG_INTERVIEW = 7

        val TITLE = "title"
        val DESC = "desc"
        val URL = "url"
        val TAG = "tag"
        val USER = "user"
        val FAVOUR_COUNT = "favour_count"
        val USER_NAME = "user.username"
        val USER_AVATAR = "user.avatar"
    }

}
