package com.leon.androidpluskotlin.ui.activity

import android.content.Intent
import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.data.model.User
import org.jetbrains.anko.startActivity

class SplashActivity: BaseActivity() {

    companion object {
        val DELAY_TIME = 2000L
    }

    override fun getLayoutResId(): Int = R.layout.activity_splash


    override fun init() {
        setStatusBarTransparent()
        val currentUser = AVUser.getCurrentUser(User::class.java)
        if (currentUser == null) {
            postDelay(Runnable {
                startActivity<LoginActivity>()
                finish()
            }, DELAY_TIME)
        } else {
            postDelay(Runnable {
                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
                startActivity<MainActivity>()
                finish()
            }, DELAY_TIME)
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}