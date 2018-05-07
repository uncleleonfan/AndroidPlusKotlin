package com.leon.androidpluskotlin.data

import android.util.SparseArray
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import com.leon.androidpluskotlin.app.DEFAULT_PAGE_SIZE
import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.data.model.UserArticleFavourMap
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDataRepository @Inject
constructor() : ArticleDataSource {

    private val mArticleByTagCache = SparseArray<MutableList<Article>>()

    private lateinit var mArticlesCache: MutableList<Article>

    private lateinit var mUserFavouredArticlesCache: MutableList<Article>

    private lateinit var mUserArticleFavourMaps: MutableList<UserArticleFavourMap>

    val lastArticleCreatedAt: Date
        get() = mArticlesCache[mArticlesCache.size - 1].createdAt

    val lastUserArticleFavourMapCreatedAt: Date
        get() = mUserArticleFavourMaps[mUserArticleFavourMaps.size - 1].createdAt

    override fun saveArticle(article: Article, callback: SaveCallback) {
        article.saveInBackground(object : com.avos.avoscloud.SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    callback.onSaveSuccess()
                } else {
                    callback.onSaveFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getArticlesByTag(tagId: Int, callback: LoadCallback<Article>) {
        getArticlesFromServer(tagId, callback, false)
    }

    private fun getArticlesFromServer(tagId: Int, callback: LoadCallback<Article>, isLoadMore: Boolean) {
        val articleAVQuery = AVQuery.getQuery(Article::class.java)
        articleAVQuery.include(Article.USER)
                .selectKeys(Arrays.asList(Article.TITLE, Article.DESC,
                        Article.FAVOUR_COUNT, Article.TAG, Article.URL,
                        Article.USER_NAME, Article.USER_AVATAR))
                .limit(DEFAULT_PAGE_SIZE)
        if (tagId == Article.TAG_HOT) {
            articleAVQuery.orderByDescending(Article.FAVOUR_COUNT).addDescendingOrder(AVObject.CREATED_AT).whereGreaterThan(Article.FAVOUR_COUNT, 0)
        } else {
            articleAVQuery.whereEqualTo(Article.TAG, tagId).orderByDescending(AVObject.CREATED_AT)
        }
        if (isLoadMore) {
            articleAVQuery.whereLessThan(AVObject.CREATED_AT, getLastTagArticleCreatedAt(tagId))

        }
        articleAVQuery.findInBackground(object : FindCallback<Article>() {
            override fun done(list: List<Article>, e: AVException?) {
                if (e == null) {
                    if (isLoadMore) {
                        mArticleByTagCache[tagId].addAll(list)
                    } else {
                        mArticleByTagCache.put(tagId, list.toMutableList())
                    }
                    callback.onLoadSuccess(list)
                } else {
                    callback.onLoadFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getMoreArticlesByTag(tagId: Int, callback: LoadCallback<Article>) {
        getArticlesFromServer(tagId, callback, true)
    }

    private fun getLastTagArticleCreatedAt(tagId: Int): Date {
        val articles = mArticleByTagCache.get(tagId)
        return articles[articles.size - 1].createdAt
    }

    override fun getArticle(position: Int, tag: Int): Article {
        return mArticleByTagCache.get(tag)[position]
    }

    override fun getUserSharedArticles(userId: String, callback: LoadCallback<Article>) {
        getUserSharedArticlesFromServer(userId, callback, false)
    }

    private fun getUserSharedArticlesFromServer(userId: String, callback: LoadCallback<Article>, isLoadMore: Boolean) {
        val articleAVQuery = AVQuery.getQuery(Article::class.java)
        try {
            val user = AVObject.createWithoutData(User::class.java, userId)
            articleAVQuery.whereEqualTo(Article.USER, user)
                    .orderByDescending(AVObject.CREATED_AT)
                    .limit(DEFAULT_PAGE_SIZE)
            if (isLoadMore) {
                articleAVQuery.whereLessThan(AVObject.CREATED_AT, lastArticleCreatedAt)
            }
            articleAVQuery.findInBackground(object : FindCallback<Article>() {
                override fun done(list: MutableList<Article>, e: AVException?) {
                    if (e == null) {
                        if (isLoadMore) {
                            mArticlesCache.addAll(list)
                        } else {
                            mArticlesCache = list
                        }
                        callback.onLoadSuccess(list)
                    } else {
                        callback.onLoadFailed(e.localizedMessage)
                    }
                }
            })
        } catch (e: AVException) {
            e.printStackTrace()
        }

    }

    override fun getMoreUserSharedArticles(userId: String, callback: LoadCallback<Article>) {
        getUserSharedArticlesFromServer(userId, callback, true)
    }

    override fun refreshUserShareArticles(userId: String, callback: LoadCallback<Article>) {
        mArticlesCache.clear()
        getUserSharedArticles(userId, callback)
    }

    override fun getShareArticle(position: Int): Article {
        return mArticlesCache[position]
    }

    override fun getUserFavouredArticles(userId: String, callback: LoadCallback<Article>) {
        getUserFavouredArticlesFromServer(userId, callback, false)
    }

    private fun getUserFavouredArticlesFromServer(userId: String, callback: LoadCallback<Article>, isLoadMore: Boolean) {
        val query = AVQuery.getQuery(UserArticleFavourMap::class.java)
        try {
            val avObject = AVObject.createWithoutData(User::class.java, userId)
            query.whereEqualTo(UserArticleFavourMap.USER, avObject)
                    .include(UserArticleFavourMap.ARTICLE)
                    .limit(DEFAULT_PAGE_SIZE)
                    .orderByDescending(AVObject.CREATED_AT)
            if (isLoadMore) {
                query.whereLessThan(AVObject.CREATED_AT, lastUserArticleFavourMapCreatedAt)
            }
            query.findInBackground(object : FindCallback<UserArticleFavourMap>() {
                override fun done(list: MutableList<UserArticleFavourMap>, e: AVException?) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserArticleFavourMaps.addAll(list)
                        } else {
                            mUserArticleFavourMaps = list
                            mUserFavouredArticlesCache = ArrayList()
                        }

                        for (i in list.indices) {
                            try {
                                val article = list[i].getAVObject("article", Article::class.java)
                                mUserFavouredArticlesCache.add(article)
                            } catch (e1: Exception) {
                                e1.printStackTrace()
                                callback.onLoadFailed(e1.localizedMessage)
                            }

                        }
                        callback.onLoadSuccess(mUserFavouredArticlesCache)
                    } else {
                        callback.onLoadFailed(e.localizedMessage)
                    }
                }
            })
        } catch (e: AVException) {
            e.printStackTrace()
            callback.onLoadFailed(e.localizedMessage)
        }

    }

    override fun refreshUserFavouredArticles(userId: String, callback: LoadCallback<Article>) {
        mUserFavouredArticlesCache.clear()
        getUserFavouredArticles(userId, callback)
    }

    override fun getMoreUserFavouredArticles(userId: String, callback: LoadCallback<Article>) {
        getUserFavouredArticlesFromServer(userId, callback, true)
    }

}
