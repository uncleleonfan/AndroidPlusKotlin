package com.leon.androidpluskotlin.app

import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.BuildConfig
import com.leon.androidpluskotlin.MyEventBusIndex
import com.leon.androidpluskotlin.data.model.*
import com.leon.androidpluskotlin.di.DaggerAppComponent
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.greenrobot.eventbus.EventBus

class AndroidPlusApp: DaggerApplication() {

    private val APP_ID = "e23RXU0ywb3H2hfHgPbmAL3s-gzGzoHsz"
    private val APP_KEY = "Hy5zNJf0I4oOAwUA7k7sf1CN"

    override fun onCreate() {
        super.onCreate()
        //Init Lean Cloud
        initSubClasses()
        AVOSCloud.initialize(this, APP_ID, APP_KEY)
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG)

        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()

        Beta.enableHotfix = false
        Beta.autoDownloadOnWifi = true
        Bugly.init(applicationContext, "033d44667d", BuildConfig.DEBUG)
    }

    private fun initSubClasses() {
        AVObject.registerSubclass(Comment::class.java)
        AVObject.registerSubclass(Question::class.java)
        AVObject.registerSubclass(Answer::class.java)
        AVObject.registerSubclass(Article::class.java)
        AVObject.registerSubclass(UserArticleFavourMap::class.java)
        AVObject.registerSubclass(UserQuestionFavourMap::class.java)
        AVObject.registerSubclass(UserAnswerFavourMap::class.java)
        AVObject.registerSubclass(User::class.java)
        AVUser.alwaysUseSubUserClass(User::class.java)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}
