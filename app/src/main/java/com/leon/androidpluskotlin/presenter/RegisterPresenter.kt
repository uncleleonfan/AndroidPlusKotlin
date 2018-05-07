package com.leon.androidpluskotlin.presenter

import com.avos.avoscloud.AVException
import com.avos.avoscloud.SignUpCallback
import com.leon.androidpluskotlin.contract.RegisterContract
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.di.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class RegisterPresenter @Inject
constructor() : RegisterContract.Presenter {

    private var mView: RegisterContract.View? = null

    override fun takeView(view: RegisterContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun register(userName: String, password: String) {
        val user = User()
        user.username = userName
        user.setPassword(password)
        user.signUpInBackground(object : SignUpCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    mView?.onRegisterSuccess()
                } else {
                    if (e.code == AVException.USERNAME_TAKEN) mView?.onUserNameTaken()
                    else mView?.onRegisterFailed()
                }
            }
        })
    }
}
