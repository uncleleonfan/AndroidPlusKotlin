package com.leon.androidpluskotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.animation.*
import com.leon.androidpluskotlin.R
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val supportActionBar = supportActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.about)
        startAnimation()

        mAboutInfo.setOnClickListener {
            val rotateAnimation = RotateAnimation(0f, 1080f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f)
            rotateAnimation.duration = 1000
            rotateAnimation.interpolator = AccelerateInterpolator()
            mAboutInfo.startAnimation(rotateAnimation)
        }
    }

    private fun startAnimation() {
        val animationSet = AnimationSet(true)
        val rotateAnimation = RotateAnimation(0f, 360f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f)

        val scaleAnimation = ScaleAnimation(1.5f, 1f, 1.5f, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f)

        val alphaAnimation = AlphaAnimation(0f, 1f)

        animationSet.addAnimation(rotateAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.addAnimation(alphaAnimation)
        animationSet.duration = 2000
        mAboutInfo.startAnimation(animationSet)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

}
