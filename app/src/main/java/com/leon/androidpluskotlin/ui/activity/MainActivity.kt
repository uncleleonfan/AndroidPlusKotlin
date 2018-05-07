package com.leon.androidpluskotlin.ui.activity

import android.animation.ObjectAnimator
import android.support.design.widget.BottomNavigationView
import android.view.animation.LinearInterpolator
import com.avos.avoscloud.feedback.FeedbackAgent
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.event.LogoutEvent
import com.leon.androidpluskotlin.event.ScrollEvent
import com.leon.androidpluskotlin.ui.fragment.ArticleFragment
import com.leon.androidpluskotlin.ui.fragment.HomeFragment
import com.leon.androidpluskotlin.ui.fragment.MeFragment
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_main


    @Inject
    lateinit var homeFragmentProvider: Lazy<HomeFragment>

    @Inject
    lateinit var articleFragmentProvider: Lazy<ArticleFragment>

    @Inject
    lateinit var meFragmentProvider: Lazy<MeFragment>


    override fun init() {
        super.init()
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mBottomNavigation.selectedItemId = R.id.main_home
        EventBus.getDefault().register(this)

        //sync feedback
        val feedbackAgent = FeedbackAgent(this)
        feedbackAgent.sync()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        switchPage(item.itemId)
        true
    }


    private fun switchPage(itemId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (itemId) {
            R.id.main_home -> fragmentTransaction.replace(R.id.mFragmentFrame, homeFragmentProvider.get())
            R.id.main_article -> fragmentTransaction.replace(R.id.mFragmentFrame, articleFragmentProvider.get())
            R.id.main_me -> fragmentTransaction.replace(R.id.mFragmentFrame, meFragmentProvider.get())
        }
        fragmentTransaction.commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onScrollChange(scrollEvent: ScrollEvent) {
        if (scrollEvent.direction === ScrollEvent.Direction.UP) {
            hideNavigationView()
        } else {
            showNavigationView()
        }
    }

    private fun showNavigationView() {
        animationNavigationView(mBottomNavigation.height.toFloat(), 0f)
    }


    private fun hideNavigationView() {
        animationNavigationView(0f, mBottomNavigation.height.toFloat())
    }
    private fun animationNavigationView(from: Float, to: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(mBottomNavigation, "translationY",
                from, to)
        objectAnimator.duration = 500
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogout(logoutEvent: LogoutEvent) {
        finish()
    }

}
