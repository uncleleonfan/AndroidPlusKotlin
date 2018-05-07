package com.leon.androidpluskotlin.contract

interface AddQuestionContract {

    interface View : BaseView {
        fun onPublishSuccess()
        fun onPublishFailed()
    }

    interface Presenter : BasePresenter<View> {
        fun publishQuestion(title: String, des: String)
    }
}
