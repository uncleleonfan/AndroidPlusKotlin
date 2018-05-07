package com.leon.androidpluskotlin.ui.activity

import android.support.design.widget.AppBarLayout
import com.avos.avoscloud.AVUser
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_USER_ID
import com.leon.androidpluskotlin.app.GlideApp
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.model.User
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.view_me_favour.*
import kotlinx.android.synthetic.main.view_me_own.*
import org.jetbrains.anko.startActivity


class ProfileActivity : BaseActivity() {


    private lateinit var mProfileUser: User
    private lateinit var mUserId: String


    override fun getLayoutResId(): Int = R.layout.activity_profile


    override fun init() {
        super.init()
        setSupportActionBar(mToolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mUserId = intent.getStringExtra(EXTRA_USER_ID)
        if (AVUser.getCurrentUser().objectId == mUserId) {
            mProfileUser = AVUser.getCurrentUser(User::class.java)
            updateUserInfo()
            initListeners()
        } else {
            loadProfileUser()
            changeToOtherUser()
        }
        setStatusBarTransparent()

    }

    private fun initListeners() {
        val pair = EXTRA_USER_ID to mProfileUser.objectId
        mAppBarLayout.addOnOffsetChangedListener(mOnOffsetChangedListener)
        mUserShare.setOnClickListener { startActivity<UserShareArticleActivity>(pair)  }
        mUserQuestions.setOnClickListener { startActivity<UserQuestionsActivity>(pair) }
        mUserAnswers.setOnClickListener { startActivity<UserAnswerActivity>(pair)}
        mUserFavourArticles.setOnClickListener { startActivity<UserFavourArticleActivity>(pair) }
        mUserFavourQuestions.setOnClickListener { startActivity<UserFavourQuestionActivity>(pair) }
        mUserFavourAnswers.setOnClickListener { startActivity<UserFavourAnswerActivity>(pair)}
    }

    private val mOnOffsetChangedListener = AppBarLayout.OnOffsetChangedListener {
        appBarLayout, verticalOffset ->
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()
        mProfileInfo.alpha = 1 - percentage
    }

    private fun changeToOtherUser() {
        mUserShare.setText(R.string.his_share)
        mUserQuestions.setText(R.string.his_questions)
        mUserAnswers.setText(R.string.his_answers)
        mUserFavourQuestions.setText(R.string.his_favour_questions)
        mUserFavourAnswers.setText(R.string.his_favour_answers)
        mUserFavourArticles.setText(R.string.his_favour_articles)
    }

    private fun loadProfileUser() {
        User.getUser(object : LoadCallback<User> {
            override fun onLoadSuccess(list: List<User>) {
                mProfileUser = list[0]
                updateUserInfo()
                initListeners()
            }

            override fun onLoadFailed(errorMsg: String) {

            }
        }, mUserId)
    }

    private fun updateUserInfo() {
        supportActionBar!!.title = mProfileUser.username
        mSlogan.text = mProfileUser.slogan
        GlideApp.with(this)
                .load(mProfileUser.avatar)
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(CircleCrop()).into(mAvatar)
    }


}
