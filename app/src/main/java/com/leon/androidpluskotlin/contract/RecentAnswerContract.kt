package com.leon.androidpluskotlin.contract


import com.leon.androidpluskotlin.data.model.Answer

interface RecentAnswerContract {

    interface View : BaseView {

        fun onLoadRecentAnswerSuccess(list: List<Answer>)

        fun onLoadRecentAnswerFailed()

        fun onLoadMoreRecentAnswerSuccess(list: List<Answer>)

        fun onLoadMoreRecentAnswerFailed()
    }

    interface Presenter : BasePresenter<View> {
        fun loadRecentAnswerByQuestion(questionId: String)

        fun loadMoreRecentAnswerByQuestion(questionId: String)
    }
}

