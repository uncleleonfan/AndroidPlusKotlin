package com.leon.androidpluskotlin.data.model

import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.DeleteCallback

import java.util.Arrays

@AVClassName("UserArticleFavourMap")
class UserArticleFavourMap : AVObject() {

    fun setUser(user: User) {
        put(USER, user)
    }

    fun setArticle(article: Article) {
        put(ARTICLE, article)
    }

    companion object {

        val USER = "user"
        val ARTICLE = "article"

        fun buildFavourMap(user: User, article: Article) {
            user.addFavourArticle(article.objectId)
            article.increaseFavourCount()
            val userArticleFavourMap = UserArticleFavourMap()
            userArticleFavourMap.setUser(user)
            userArticleFavourMap.setArticle(article)
            userArticleFavourMap.saveInBackground()
        }

        fun breakFavourMap(user: User, article: Article) {
            user.removeFavouredArticle(article.objectId)
            article.decreaseFavourCount()
            val userQuery = AVQuery.getQuery(UserArticleFavourMap::class.java)
            userQuery.whereEqualTo(USER, user)
            val articleQuery = AVQuery.getQuery(UserArticleFavourMap::class.java)
            articleQuery.whereEqualTo(ARTICLE, article)
            val query = AVQuery.and(Arrays.asList(userQuery, articleQuery))
            query.deleteAllInBackground(object : DeleteCallback() {
                override fun done(e: AVException?) {
                    if (e == null) {
                    }
                }
            })
        }
    }
}
