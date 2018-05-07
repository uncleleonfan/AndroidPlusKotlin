package com.leon.androidpluskotlin.ui.activity

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_PASSWORD
import com.leon.androidpluskotlin.app.EXTRA_USER_NAME
import com.leon.androidpluskotlin.app.RESULT_CODE
import com.leon.androidpluskotlin.contract.RegisterContract
import com.leon.androidpluskotlin.presenter.RegisterPresenter
import com.leon.androidpluskotlin.utils.isValidPassword
import com.leon.androidpluskotlin.utils.isValidUserName
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class RegisterActivity: BaseActivity(), RegisterContract.View, View.OnFocusChangeListener{


    @Inject
    lateinit var mRegisterPresenter: RegisterPresenter

    override fun getLayoutResId(): Int = R.layout.activity_register


    override fun init() {
        super.init()
        mUserName.onFocusChangeListener = this
        mConfirmPassword.onFocusChangeListener = this
        mPassword.onFocusChangeListener = this
        mRegister.setOnClickListener {
            hideSoftKeyboard()
            register()
        }

        mConfirmPassword.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, keyEvent ->
            register()
            true
        })
    }

    override fun onResume() {
        super.onResume()
        mRegisterPresenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRegisterPresenter.dropView()
    }


    private fun register() {
        hideSoftKeyboard()
        val userName = mUserName.text.toString().trim()
        val password = mPassword.text.toString().trim()
        val confirmPassword = mConfirmPassword.text.toString().trim()

        if (checkUserName(userName)) {
            if (checkPassword(password)) {
                if (confirmPassword == password) {
                    mProgressLayout.visibility = View.VISIBLE
                    mConfirmPasswordInputLayout.isErrorEnabled = false
                    mRegisterPresenter.register(userName, password)
                } else {
                    mConfirmPasswordInputLayout.isErrorEnabled = true
                    mConfirmPasswordInputLayout.error = getString(R.string.error_confirm_password)
                }
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


    override fun onFocusChange(view: View, b: Boolean) {
        when (view.id) {
            R.id.mUserName -> mUserNameInputLayout.isErrorEnabled = false
            R.id.mPassword -> mPasswordInputLayout.isErrorEnabled = false
            R.id.mConfirmPassword -> mConfirmPasswordInputLayout.isErrorEnabled = false
        }
    }

    override fun onRegisterFailed() {
        mProgressLayout.visibility = View.GONE
        toast(R.string.register_failed)
    }

    override fun onRegisterSuccess() {
        mProgressLayout.visibility = View.GONE
        toast(R.string.register_success)
        val intent = Intent()
        intent.putExtra(EXTRA_USER_NAME, mUserName.text.toString().trim())
        intent.putExtra(EXTRA_PASSWORD, mPassword.text.toString().trim())
        setResult(RESULT_CODE, intent)
        finish()
    }

    override fun onUserNameTaken() {
        mProgressLayout.visibility = View.GONE
        toast(R.string.user_name_taken)
    }

}