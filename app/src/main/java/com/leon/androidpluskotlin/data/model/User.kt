package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.*
import com.leon.androidpluskotlin.data.LoadCallback
import org.json.JSONArray
import org.json.JSONException
import java.util.*

@AVClassName("User")
class User : AVUser() {

    var avatar: String?
        get() = getString("avatar")
        set(avatarUrl) = put("avatar", avatarUrl)

    var slogan: String?
        get() = getString("slogan")
        set(slogan) {
            put("slogan", slogan)
            saveInBackground()
        }

    fun addLikedAnswer(answerId: String) {
        add("liked", answerId)
    }

    fun addFavourQuestion(questionId: String) {
        add("favoured_question", questionId)
    }

    fun addFavourArticle(objectId: String) {
        add("favoured_article", objectId)
        saveInBackground()
    }


    fun isLikedAnswer(objectId: String): Boolean {
        val liked = getJSONArray("liked")
        return hasRecord(objectId, liked)
    }

    fun isFavouredQuestion(objectId: String): Boolean {
        val favoured = getJSONArray("favoured_question")
        return hasRecord(objectId, favoured)
    }

    fun isFavouredArticle(objectId: String): Boolean {
        val favoured = getJSONArray("favoured_article")
        return hasRecord(objectId, favoured)
    }

    fun removeLikedAnswer(objectId: String) {
        removeAll("liked", Arrays.asList(objectId))
        saveInBackground()
    }

    fun removeFavouredQuestion(objectId: String) {
        removeAll("favoured_question", Arrays.asList(objectId))
    }

    fun removeFavouredArticle(objectId: String) {
        removeAll("favoured_article", Arrays.asList(objectId))
        saveInBackground()
    }

    private fun hasRecord(objectId: String, jsonArray: JSONArray?): Boolean {
        if (jsonArray == null) {
            return false
        }
        for (i in 0 until jsonArray.length()) {
            try {
                val id = jsonArray.getString(i)
                if (id == objectId) {
                    return true
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        return false
    }

    companion object {

        fun getUser(loadCallback: LoadCallback<User>, objectId: String) {
            val userAVQuery = AVQuery.getQuery(User::class.java)
            userAVQuery.whereEqualTo(AVUser.OBJECT_ID, objectId)
            userAVQuery.findInBackground(object : FindCallback<User>() {
                override fun done(list: List<User>, e: AVException?) {
                    if (e == null) {
                        loadCallback.onLoadSuccess(list)
                    } else {
                        loadCallback.onLoadFailed(e.localizedMessage)
                    }

                }
            })

        }
    }
}
