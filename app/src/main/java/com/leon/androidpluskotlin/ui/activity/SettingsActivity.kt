package com.leon.androidpluskotlin.ui.activity

import android.support.v7.app.AlertDialog
import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.BuildConfig
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.event.LogoutEvent
import kotlinx.android.synthetic.main.activity_settings.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton


class SettingsActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_settings


    override fun init() {
        super.init()
        val supportActionBar = supportActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.settings)
        val version = String.format(getString(R.string.version),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        mVersion.text = version

        mAbout.setOnClickListener { startActivity<AboutActivity>() }
        mLogout.setOnClickListener {logout()}
    }

    private fun logout() {
        AlertDialog.Builder(this).setTitle(R.string.logout)
                .setMessage(R.string.confirm_logout)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm) { dialog, which ->
                    AVUser.logOut()
                    startActivity<LoginActivity>()
                    EventBus.getDefault().post(LogoutEvent())
                }.show()
        alert(R.string.confirm_logout, R.string.logout) {
            cancelButton {  }
            yesButton {
                AVUser.logOut()
                startActivity<LoginActivity>()
                EventBus.getDefault().post(LogoutEvent())
            }
        }.show()
    }

}
