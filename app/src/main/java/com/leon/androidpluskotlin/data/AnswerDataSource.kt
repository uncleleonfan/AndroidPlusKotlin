package com.leon.androidpluskotlin.data


import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Question

interface AnswerDataSource {

    fun getRecentAnswerList(callback: LoadCallback<Answer>)

    fun getMoreRecentAnswerList(callback: LoadCallback<Answer>)

    fun getRecentAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>)

    fun getMoreRecentAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>)

    fun getHotAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>)

    fun getMoreHotAnswerListByQuestion(questionId: String, callback: LoadCallback<Answer>)

    fun addAnswerToQuestion(answerString: String, question: Question, callback: SaveCallback)

    fun getUserAnswerList(userId: String, callback: LoadCallback<Answer>)

    fun getMoreUserAnswerList(userId: String, callback: LoadCallback<Answer>)

    fun refreshUserAnswerList(userId: String, callback: LoadCallback<Answer>)

    fun getUserFavouredAnswers(userId: String, callback: LoadCallback<Answer>)

    fun getMoreUserFavouredAnswers(userId: String, callback: LoadCallback<Answer>)

    fun refreshUserFavouredAnswers(userId: String, callback: LoadCallback<Answer>)
}
