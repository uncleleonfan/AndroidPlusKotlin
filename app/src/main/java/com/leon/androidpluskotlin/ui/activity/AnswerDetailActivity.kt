package com.leon.androidpluskotlin.ui.activity

import android.os.Build
import android.transition.ArcMotion
import android.transition.ChangeBounds
import android.view.MenuItem
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_ANSWER
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.data.model.UserAnswerFavourMap
import com.leon.androidpluskotlin.utils.getDateTimeFormat
import kotlinx.android.synthetic.main.activity_answer_detail.*
import org.jetbrains.anko.startActivity


class AnswerDetailActivity : BaseActivity() {

    private var isUp = false
    private lateinit var mAnswer: Answer

    override fun getLayoutResId(): Int = R.layout.activity_answer_detail


    override fun init() {
        super.init()
        val serialised = intent.getStringExtra(EXTRA_ANSWER)
        try {
            mAnswer = AVObject.parseAVObject(serialised) as Answer
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mQuestionTitle.transitionName = getString(R.string.question_title_transition)
            mAnswerText.transitionName = getString(R.string.answer_transition)
            val changeBounds = ChangeBounds()
            changeBounds.pathMotion = ArcMotion()
            window.sharedElementEnterTransition = changeBounds
        }

        initActionBar()
        initViews()
        initListeners()
    }

    private fun initListeners() {
        mThumbUp.setOnClickListener { handleThumbUp() }
        mComment.setOnClickListener { startActivity<CommentActivity>(EXTRA_ANSWER to intent.getStringExtra(EXTRA_ANSWER)) }
    }

    private fun initViews() {
        val user = AVUser.getCurrentUser(User::class.java)
        if (user.isLikedAnswer(mAnswer.objectId)) {
            isUp = true
            mThumbUp.setColorFilter(resources.getColor(R.color.colorPrimary))
        }

        mQuestionTitle.text = mAnswer.question.title
        mAnswerText.text = mAnswer.content
        mPublishDate.text = getDateTimeFormat(mAnswer.createdAt)
    }

    private fun initActionBar() {
        setSupportActionBar(mToolBar)
        val supportActionBar = supportActionBar
        val userName = mAnswer.user.username
        val title = String.format(getString(R.string.user_answer), userName)
        supportActionBar!!.setTitle(title)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
        }
        return true
    }


    private fun handleThumbUp() {
        isUp = !isUp
        val user = AVUser.getCurrentUser(User::class.java)
        if (isUp) {
            mThumbUp.setColorFilter(resources.getColor(R.color.colorPrimary))
            UserAnswerFavourMap.buildFavourMap(user, mAnswer)

        } else {
            mThumbUp.setColorFilter(resources.getColor(R.color.primary_light))
            UserAnswerFavourMap.breakFavourMap(user, mAnswer)
        }
    }
}
