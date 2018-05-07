package com.leon.androidpluskotlin.contract


import com.leon.androidpluskotlin.data.model.Question

interface RecentQuestionContract {

    interface View : BaseView {
        fun onLoadRecentQuestionSuccess(list: List<Question>)
        fun onLoadRecentQuestionFailed()

        fun onLoadMoreRecentQuestionSuccess()
        fun onLoadMoreRecentQuestionFailed()
    }

    interface Presenter : BasePresenter<View> {
        fun loadRecentQuestions()
        fun loadMoreRecentQuestions()
    }
}
