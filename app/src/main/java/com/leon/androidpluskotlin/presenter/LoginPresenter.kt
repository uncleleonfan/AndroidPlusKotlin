package com.leon.androidpluskotlin.presenter

import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.leon.androidpluskotlin.contract.LoginContract
import com.leon.androidpluskotlin.di.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class LoginPresenter @Inject
constructor() : LoginContract.Presenter {

    private var mView: LoginContract.View? = null

    override fun takeView(view: LoginContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun login(userName: String, password: String) {
        AVUser.logInInBackground(userName, password, object : LogInCallback<AVUser>() {
            override fun done(avUser: AVUser?, e: AVException?) {
                if (e == null) {
                    mView?.onLoginSuccess()
                } else {
                    handleError(e.code)
                }
            }
        })
    }

    private fun handleError(code: Int) {
        when (code) {
            AVException.USERNAME_PASSWORD_MISMATCH -> mView?.onUserNamePasswordMismatch()
            AVException.USER_DOESNOT_EXIST -> mView?.onUserNameDoesNotExist()
            else -> mView?.onLoginFailed()
        }
    }
}
