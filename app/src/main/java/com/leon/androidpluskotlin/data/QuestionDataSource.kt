package com.leon.androidpluskotlin.data


import com.leon.androidpluskotlin.data.model.Question

interface QuestionDataSource {

    fun getRecentQuestionList(callback: LoadCallback<Question>)

    fun getMoreRecentQuestionList(callback: LoadCallback<Question>)

    fun getHotQuestionList(callback: LoadCallback<Question>)

    fun getMoreHotQuestionList(callback: LoadCallback<Question>)

    fun getUserQuestions(userId: String, callback: LoadCallback<Question>)

    fun getMoreUserQuestions(userId: String, callback: LoadCallback<Question>)

    fun refreshUserQuestions(userId: String, callback: LoadCallback<Question>)

    fun getUserFavouredQuestions(userId: String, callback: LoadCallback<Question>)

    fun getMoreUserFavouredQuestions(userId: String, callback: LoadCallback<Question>)

    fun refreshUserFavouredQuestions(userId: String, callback: LoadCallback<Question>)

    fun addQuestion(title: String, des: String, callback: SaveCallback)

}
