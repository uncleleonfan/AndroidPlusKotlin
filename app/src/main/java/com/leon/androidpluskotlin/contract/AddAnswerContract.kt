package com.leon.androidpluskotlin.contract


import com.leon.androidpluskotlin.data.model.Question

interface AddAnswerContract {

    interface View : BaseView {
        fun onPublishAnswerSuccess()
        fun onPublishAnswerFailed()
    }

    interface Presenter : BasePresenter<View> {
        fun publishAnswer(answer: String, question: Question)
    }
}
