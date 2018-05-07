package com.leon.androidpluskotlin.data

import com.avos.avoscloud.*
import com.leon.androidpluskotlin.app.DEFAULT_PAGE_SIZE
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.data.model.UserQuestionFavourMap
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionDataRepository @Inject
constructor() : QuestionDataSource {

    private lateinit var mRecentQuestionListCache: MutableList<Question>

    private lateinit var mHotQuestionListCache: MutableList<Question>

    private lateinit var mUserQuestionListCache: MutableList<Question>

    private lateinit var mUserQuestionFavourMaps: MutableList<UserQuestionFavourMap>

    private lateinit var mUserFavouredQuestionsCache: MutableList<Question>

    private val lastQuestionCreateAt: Date
        get() = mRecentQuestionListCache[mRecentQuestionListCache.size - 1].createdAt


    val lastHotQuestionCreatedAt: Date
        get() = mHotQuestionListCache[mHotQuestionListCache.size - 1].createdAt

    val lastUserQuestionCreatedAt: Date
        get() = mUserQuestionListCache[mUserQuestionListCache.size - 1].createdAt

    val lastUserQuestionFavourMapCreatedAt: Date
        get() = mUserFavouredQuestionsCache[mUserFavouredQuestionsCache.size - 1].createdAt

    override fun getRecentQuestionList(callback: LoadCallback<Question>) {
        getRecentQuestionListFromServer(callback, false)
    }

    private fun getRecentQuestionListFromServer(callback: LoadCallback<Question>, isLoadMore: Boolean) {
        val questionAVQuery = AVObject.getQuery(Question::class.java)
        questionAVQuery.limit(DEFAULT_PAGE_SIZE)
                .include(Question.USER)
                .selectKeys(Arrays.asList(Question.TITLE,
                        Question.DESC, Question.FAVOUR_COUNT, Question.ANSWER_COUNT,
                        Question.USER_NAME, Question.USER_AVATAR))
                .orderByDescending(AVObject.CREATED_AT)
        if (isLoadMore) {
            questionAVQuery.whereLessThan(AVObject.CREATED_AT, lastQuestionCreateAt)

        }
        questionAVQuery.findInBackground(object : FindCallback<Question>() {
            override fun done(list: MutableList<Question>, e: AVException?) {
                if (e == null) {
                    if (isLoadMore) {
                        mRecentQuestionListCache.addAll(list)
                    } else {
                        mRecentQuestionListCache = list
                    }
                    callback.onLoadSuccess(list)
                } else {
                    callback.onLoadFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getMoreRecentQuestionList(callback: LoadCallback<Question>) {
        getRecentQuestionListFromServer(callback, true)
    }

    override fun getHotQuestionList(callback: LoadCallback<Question>) {
        getHotQuestionListFromServer(callback, false)
    }

    private fun getHotQuestionListFromServer(callback: LoadCallback<Question>, isLoadMore: Boolean) {
        val questionAVQuery = AVObject.getQuery(Question::class.java)
        questionAVQuery.limit(DEFAULT_PAGE_SIZE).include(Question.USER)
                .selectKeys(Arrays.asList(Question.USER_AVATAR, Question.USER_NAME, Question.TITLE, Question.DESC, Question.FAVOUR_COUNT, Question.ANSWER_COUNT))
                .orderByDescending(Question.FAVOUR_COUNT)
                .addDescendingOrder(AVObject.CREATED_AT)
                .whereGreaterThan(Question.FAVOUR_COUNT, 0)
        if (isLoadMore) {
            questionAVQuery.whereLessThan(AVObject.CREATED_AT, lastHotQuestionCreatedAt)
        }
        questionAVQuery.findInBackground(object : FindCallback<Question>() {
            override fun done(list: MutableList<Question>, e: AVException?) {
                if (e == null) {
                    if (isLoadMore) {
                        mHotQuestionListCache.addAll(list)
                    } else {
                        mHotQuestionListCache = list
                    }
                    callback.onLoadSuccess(list)
                } else {
                    callback.onLoadFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getMoreHotQuestionList(callback: LoadCallback<Question>) {
        getHotQuestionListFromServer(callback, true)
    }

    override fun getUserQuestions(userId: String, callback: LoadCallback<Question>) {
        getUserQuestionsFromServer(userId, callback, false)
    }

    private fun getUserQuestionsFromServer(userId: String, callback: LoadCallback<Question>, isLoadMore: Boolean) {
        val questionAVQuery = AVQuery.getQuery(Question::class.java)
        try {
            val user = AVUser.createWithoutData(User::class.java, userId)
            questionAVQuery.whereEqualTo(Question.USER, user)
                    .orderByDescending(AVObject.CREATED_AT)
                    .limit(DEFAULT_PAGE_SIZE)
            if (isLoadMore) {
                questionAVQuery.whereLessThan(AVObject.CREATED_AT, lastUserQuestionCreatedAt)
            }
            questionAVQuery.findInBackground(object : FindCallback<Question>() {
                override fun done(list: MutableList<Question>, e: AVException?) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserQuestionListCache.addAll(list)
                        } else {
                            mUserQuestionListCache = list
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

    override fun getMoreUserQuestions(userId: String, callback: LoadCallback<Question>) {
        getUserQuestionsFromServer(userId, callback, true)
    }

    override fun refreshUserQuestions(userId: String, callback: LoadCallback<Question>) {
        mUserQuestionListCache.clear()
        getUserQuestions(userId, callback)
    }

    override fun getUserFavouredQuestions(userId: String, callback: LoadCallback<Question>) {
        getUserFavouredQuestionsFromServer(userId, callback, false)
    }

    override fun getMoreUserFavouredQuestions(userId: String, callback: LoadCallback<Question>) {
        getUserFavouredQuestionsFromServer(userId, callback, true)
    }


    private fun getUserFavouredQuestionsFromServer(userId: String, callback: LoadCallback<Question>, isLoadMore: Boolean) {
        val query = AVQuery.getQuery(UserQuestionFavourMap::class.java)
        try {
            val avObject = AVObject.createWithoutData(User::class.java, userId)
            query.whereEqualTo(UserQuestionFavourMap.USER, avObject)
                    .include(UserQuestionFavourMap.QUESTION)
                    .limit(DEFAULT_PAGE_SIZE)
                    .orderByDescending(AVObject.CREATED_AT)
            if (isLoadMore) {
                query.whereLessThan(AVObject.CREATED_AT, lastUserQuestionFavourMapCreatedAt)
            }
            query.findInBackground(object : FindCallback<UserQuestionFavourMap>() {
                override fun done(list: MutableList<UserQuestionFavourMap>, e: AVException?) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserQuestionFavourMaps.addAll(list)
                        } else {
                            mUserQuestionFavourMaps = list
                            mUserFavouredQuestionsCache = ArrayList()
                        }
                        for (i in list.indices) {
                            try {
                                val question = list[i].getAVObject(UserQuestionFavourMap.QUESTION, Question::class.java)
                                mUserFavouredQuestionsCache.add(question)
                            } catch (e1: Exception) {
                                e1.printStackTrace()
                                callback.onLoadFailed(e1.localizedMessage)
                            }

                        }
                        callback.onLoadSuccess(mUserFavouredQuestionsCache)
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

    override fun refreshUserFavouredQuestions(userId: String, callback: LoadCallback<Question>) {
        mUserFavouredQuestionsCache.clear()
        getUserFavouredQuestions(userId, callback)
    }

    override fun addQuestion(title: String, des: String, callback: SaveCallback) {
        val question = Question()
        question.title = title
        question.description = des
        val user = AVUser.getCurrentUser(User::class.java)
        question.user = user
        question.saveInBackground(object : com.avos.avoscloud.SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    callback.onSaveSuccess()
                } else {
                    callback.onSaveFailed(e.localizedMessage)
                }
            }
        })
    }
}
