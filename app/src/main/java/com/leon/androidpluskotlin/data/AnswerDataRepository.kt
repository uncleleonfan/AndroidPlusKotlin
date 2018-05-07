package com.leon.androidpluskotlin.data

import com.avos.avoscloud.*
import com.leon.androidpluskotlin.app.DEFAULT_PAGE_SIZE
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.data.model.UserAnswerFavourMap
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnswerDataRepository @Inject constructor() : AnswerDataSource {

    private lateinit var mRecentAnswerListCache: MutableList<Answer>

    private lateinit var mHotAnswerListCache: MutableList<Answer>

    private lateinit var mUserAnswerListCache: MutableList<Answer>

    private lateinit var mUserAnswerFavourMaps: MutableList<UserAnswerFavourMap>

    private lateinit var mMyFavouredAnswerCache: MutableList<Answer>


    private val lastRecentAnswerCreatedAt: Date
        get() = mRecentAnswerListCache[mRecentAnswerListCache.size - 1].createdAt

    val lastHotAnswerCreatedAt: Date
        get() = mHotAnswerListCache[mHotAnswerListCache.size - 1].createdAt

    val lastUserAnswerCreatedAt: Date
        get() = mUserAnswerListCache[mUserAnswerListCache.size - 1].createdAt

    val lastUserAnswerFavourMapCreatedAt: Date
        get() = mUserAnswerFavourMaps[mUserAnswerFavourMaps.size - 1].createdAt

    private fun getQuestionWithoutData(questionId: String): Question {
        return AVObject.createWithoutData(Question::class.java, questionId)
    }

    override fun addAnswerToQuestion(answerString: String, question: Question, callback: SaveCallback) {
        val answer = Answer()
        answer.setAnswer(answerString)
        val currentUser = AVUser.getCurrentUser(User::class.java)
        answer.user = currentUser
        answer.question = getQuestionWithoutData(question.objectId)
        answer.saveInBackground(object : com.avos.avoscloud.SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    question.addAnswerCount()
                    callback.onSaveSuccess()
                } else {
                    callback.onSaveFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getRecentAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>) {
        getAnswerListByQuestionFromServer(questionId, callback, false)
    }

    fun getAnswerListByQuestionFromServer(questionId: String, callback: LoadCallback<Answer>, isLoadMore: Boolean) {
        val answerAVQuery = AVObject.getQuery(Answer::class.java)
        val question = getQuestionWithoutData(questionId)
        answerAVQuery.limit(DEFAULT_PAGE_SIZE)
                .include(Answer.USER)
                .include(Answer.QUESTION)
                .selectKeys(Arrays.asList(Answer.CONTENT,
                        Answer.LIKE_COUNT,
                        Answer.COMMENT_COUNT,
                        Answer.USER_NAME,
                        Answer.USER_AVATAR,
                        Answer.QUESTION_TITLE))
                .whereEqualTo(Answer.QUESTION, question)
                .orderByDescending(AVObject.CREATED_AT)
        if (isLoadMore) {
            answerAVQuery.whereLessThan(AVObject.CREATED_AT, lastRecentAnswerCreatedAt)
        }
        answerAVQuery.findInBackground(object : FindCallback<Answer>() {
            override fun done(list: MutableList<Answer>, e: AVException?) {
                if (e == null) {
                    if (isLoadMore) {
                        mRecentAnswerListCache.addAll(list)
                    } else {
                        mRecentAnswerListCache = list
                    }
                    callback.onLoadSuccess(list)
                } else {
                    callback.onLoadFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getMoreRecentAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>) {
        getAnswerListByQuestionFromServer(questionId, callback, true)
    }

    override fun getRecentAnswerList(callback: LoadCallback<Answer>) {
        getRecentAnswerListFromServer(callback, false)
    }

    private fun getRecentAnswerListFromServer(callback: LoadCallback<Answer>, isLoadMore: Boolean) {
        val answerAVQuery = AVObject.getQuery(Answer::class.java)
        answerAVQuery.limit(DEFAULT_PAGE_SIZE).include(Answer.USER).include(Answer.QUESTION)
                .selectKeys(Arrays.asList(Answer.USER_NAME, Answer.QUESTION_TITLE, Answer.LIKE_COUNT, Answer.COMMENT_COUNT, Answer.CONTENT))
                .orderByDescending(AVObject.CREATED_AT)
        if (isLoadMore) {
            answerAVQuery.whereLessThan(AVObject.CREATED_AT, lastRecentAnswerCreatedAt)
        }
        answerAVQuery.findInBackground(object : FindCallback<Answer>() {
            override fun done(list: MutableList<Answer>, e: AVException?) {
                if (e == null) {
                    if (isLoadMore) {
                        mRecentAnswerListCache.addAll(list)
                    } else {
                        mRecentAnswerListCache = list
                    }
                    callback.onLoadSuccess(list)
                } else {
                    callback.onLoadFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getMoreRecentAnswerList(callback: LoadCallback<Answer>) {
        getRecentAnswerListFromServer(callback, true)
    }


    override fun getHotAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>) {
        getHotAnswerListByQuestionFromServer(questionId, callback, false)
    }

    private fun getHotAnswerListByQuestionFromServer(questionId: String, callback: LoadCallback<Answer>, isLoadMore: Boolean) {
        val answerAVQuery = AVObject.getQuery(Answer::class.java)
        val question = getQuestionWithoutData(questionId)
        answerAVQuery.limit(DEFAULT_PAGE_SIZE).include(Answer.USER).include(Answer.QUESTION)
                .selectKeys(Arrays.asList(Answer.USER_NAME, Answer.USER_AVATAR,
                        Answer.LIKE_COUNT, Answer.COMMENT_COUNT, Answer.CONTENT, Answer.QUESTION_TITLE))
                .whereEqualTo(Answer.QUESTION, question)
                .whereGreaterThan(Answer.LIKE_COUNT, 0)
                .orderByDescending(Answer.LIKE_COUNT)
                .addDescendingOrder(AVObject.CREATED_AT)
        if (isLoadMore) {
            answerAVQuery.whereLessThan(AVObject.CREATED_AT, lastHotAnswerCreatedAt)
        }
        answerAVQuery.findInBackground(object : FindCallback<Answer>() {
            override fun done(list: MutableList<Answer>, e: AVException?) {
                if (e == null) {
                    if (isLoadMore) {
                        mHotAnswerListCache.addAll(list)
                    } else {
                        mHotAnswerListCache = list
                    }
                    callback.onLoadSuccess(list)
                } else {
                    callback.onLoadFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun getMoreHotAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>) {
        getHotAnswerListByQuestionFromServer(questionId, callback, true)
    }


    override fun getUserAnswerList(userId: String, callback: LoadCallback<Answer>) {
        getUserAnswerListFromServer(userId, callback, false)
    }

    private fun getUserAnswerListFromServer(userId: String, callback: LoadCallback<Answer>, isLoadMore: Boolean) {
        val query = AVObject.getQuery(Answer::class.java)
        try {
            val user = AVObject.createWithoutData(User::class.java, userId)
            query.orderByDescending(AVObject.CREATED_AT)
                    .include(Answer.QUESTION)
                    .include(Answer.USER)
                    .selectKeys(Arrays.asList(Answer.QUESTION_TITLE, Answer.CONTENT, Answer.LIKE_COUNT, Answer.COMMENT_COUNT, Answer.USER_NAME))
                    .whereEqualTo(Answer.USER, user)
                    .limit(DEFAULT_PAGE_SIZE)
            if (isLoadMore) {
                query.whereLessThan(AVObject.CREATED_AT, lastUserAnswerCreatedAt)
            }
            query.findInBackground(object : FindCallback<Answer>() {
                override fun done(list: MutableList<Answer>, e: AVException?) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserAnswerListCache.addAll(list)
                        } else {
                            mUserAnswerListCache = list
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

    override fun getMoreUserAnswerList(userId: String, callback: LoadCallback<Answer>) {
        getUserAnswerListFromServer(userId, callback, true)
    }

    override fun refreshUserAnswerList(userId: String, callback: LoadCallback<Answer>) {
        mUserAnswerListCache.clear()
        getUserAnswerList(userId, callback)
    }

    override fun getUserFavouredAnswers(userId: String, callback: LoadCallback<Answer>) {
        getUserFavouredAnswersFromServer(userId, callback, false)
    }

    override fun getMoreUserFavouredAnswers(userId: String, callback: LoadCallback<Answer>) {
        getUserFavouredAnswersFromServer(userId, callback, true)
    }

    override fun refreshUserFavouredAnswers(userId: String, callback: LoadCallback<Answer>) {
        mUserAnswerListCache.clear()
        getUserFavouredAnswers(userId, callback)
    }


    private fun getUserFavouredAnswersFromServer(userId: String, callback: LoadCallback<Answer>, isLoadMore: Boolean) {
        val query = AVQuery.getQuery(UserAnswerFavourMap::class.java)
        try {
            val avObject = AVObject.createWithoutData(User::class.java, userId)
            query.whereEqualTo(UserAnswerFavourMap.USER, avObject)
                    .include(UserAnswerFavourMap.ANSWER_USER)
                    .include(UserAnswerFavourMap.ANSWER)
                    .include(UserAnswerFavourMap.ANSWER_QUESTION)
                    .selectKeys(Arrays.asList(UserAnswerFavourMap.ANSWER_COMMENT_COUNT,
                            UserAnswerFavourMap.ANSWER_USER_USERNAME,
                            UserAnswerFavourMap.ANSWER_CONTENT,
                            UserAnswerFavourMap.ANSWER_LIKE_COUNT,
                            UserAnswerFavourMap.ANSWER_QUESTION_TITLE))
                    .limit(DEFAULT_PAGE_SIZE)
                    .orderByDescending(AVObject.CREATED_AT)
            if (isLoadMore) {
                query.whereLessThan(AVObject.CREATED_AT, lastUserAnswerFavourMapCreatedAt)
            }
            query.findInBackground(object : FindCallback<UserAnswerFavourMap>() {
                override fun done(list: MutableList<UserAnswerFavourMap>, e: AVException?) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserAnswerFavourMaps.addAll(list)
                        } else {
                            mUserAnswerFavourMaps = list
                            mMyFavouredAnswerCache = ArrayList()
                        }
                        for (i in list.indices) {
                            try {
                                val answer = list[i].getAVObject(UserAnswerFavourMap.ANSWER, Answer::class.java)
                                mMyFavouredAnswerCache.add(answer)
                            } catch (e1: Exception) {
                                e1.printStackTrace()
                                callback.onLoadFailed(e1.localizedMessage)
                            }

                        }
                        callback.onLoadSuccess(mMyFavouredAnswerCache)
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
}
