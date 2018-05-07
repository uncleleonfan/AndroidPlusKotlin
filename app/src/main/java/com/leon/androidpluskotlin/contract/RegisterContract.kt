package com.leon.androidpluskotlin.contract

interface RegisterContract {

    interface View : BaseView {
        fun onRegisterSuccess()
        fun onRegisterFailed()
        fun onUserNameTaken()
    }

    interface Presenter : BasePresenter<View> {
        fun register(userName: String, password: String)
    }
}
