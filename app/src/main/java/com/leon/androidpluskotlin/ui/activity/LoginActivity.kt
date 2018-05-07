package com.leon.androidpluskotlin.ui.activity

import android.content.Intent
import android.view.View
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_PASSWORD
import com.leon.androidpluskotlin.app.EXTRA_USER_NAME
import com.leon.androidpluskotlin.app.REQUEST_CODE
import com.leon.androidpluskotlin.contract.LoginContract
import com.leon.androidpluskotlin.presenter.LoginPresenter
import com.leon.androidpluskotlin.utils.checkPermissions
import com.leon.androidpluskotlin.utils.isValidPassword
import com.leon.androidpluskotlin.utils.isValidUserName
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.View {

    @Inject
    lateinit var mLoginPresenter: LoginPresenter


    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun init() {
        super.init()
        mPassword.setOnEditorActionListener{ textView, i, keyEvent ->
            login()
            true
        }
        mRegister.setOnClickListener {startActivityForResult<RegisterActivity>(REQUEST_CODE)}
        mLogin.setOnClickListener { login() }
        checkPermissions()

    }


    override fun onResume() {
        super.onResume()
        mLoginPresenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLoginPresenter.dropView()
    }


    private fun login() {
        hideSoftKeyboard()
        val userName = mUserName.text.toString().trim()
        val password = mPassword.text.toString().trim()
        if (checkUserName(userName)) {
            if (checkPassword(password)) {
                mProgressLayout.visibility = View.VISIBLE
                mLoginPresenter.login(userName, password)
            }
        }
    }

    private fun checkPassword(password: String): Boolean {
        if (password.isEmpty()) {
            mPasswordInputLayout.isErrorEnabled = true
            mPasswordInputLayout.error = getString(R.string.error_password_empty)
            return false
        } else if (!password.isValidPassword()) {
            mPasswordInputLayout.isErrorEnabled = true
            mPasswordInputLayout.error = getString(R.string.error_password)
            return false
        }
        return true
    }

    private fun checkUserName(userName: String): Boolean {
        if (userName.isEmpty()) {
            mUserNameInputLayout.isErrorEnabled = true
            mUserNameInputLayout.error = getString(R.string.error_user_name_empty)
            return false
        } else if (!userName.isValidUserName()) {
            mUserNameInputLayout.isErrorEnabled = true
            mUserNameInputLayout.error = getString(R.string.error_user_name)
            return false
        }
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mUserName.setText(data?.getStringExtra(EXTRA_USER_NAME))
        mPassword.setText(data?.getStringExtra(EXTRA_PASSWORD))
    }


    override fun onLoginSuccess() {
        mProgressLayout.visibility = View.GONE
        toast(R.string.login_success)
        startActivity<MainActivity>()
    }

    override fun onLoginFailed() {
        mProgressLayout.visibility = View.GONE
        toast(R.string.login_failed)

    }

    override fun onUserNamePasswordMismatch() {
        mProgressLayout.visibility = View.GONE
        toast(R.string.user_name_password_not_match)
    }


    override fun onUserNameDoesNotExist() {
        mProgressLayout.visibility = View.GONE
        toast(R.string.user_name_does_not_exist)
    }

}