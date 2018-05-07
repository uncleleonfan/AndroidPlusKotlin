package com.leon.androidpluskotlin.contract


import com.leon.androidpluskotlin.data.model.Answer

interface HotAnswerContract {

    interface View : BaseView {

        fun onLoadHotAnswerSuccess(list: List<Answer>)

        fun onLoadHotAnswerFailed()

        fun onLoadMoreHotAnswerSuccess(list: List<Answer>)

        fun onLoadMoreHotAnswerFailed()
    }

    interface Presenter : BasePresenter<View> {
        fun loadHotAnswerByQuestion(questionId: String)

        fun loadMoreHotAnswerByQuestion(questionId: String)
    }
}

