package com.leon.androidpluskotlin.contract

interface LoginContract {

    interface View : BaseView {
        fun onLoginSuccess()
        fun onLoginFailed()
        fun onUserNamePasswordMismatch()
        fun onUserNameDoesNotExist()
    }

    interface Presenter : BasePresenter<View> {
        fun login(userName: String, password: String)
    }
}
