package com.leon.androidpluskotlin.ui.activity

import android.os.Build
import android.transition.ArcMotion
import android.transition.ChangeBounds
import android.view.MenuItem
import android.view.View
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.QuestionDetailPagerAdapter
import com.leon.androidpluskotlin.app.EXTRA_QUESTION
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.data.model.UserQuestionFavourMap
import kotlinx.android.synthetic.main.activity_question_detail.*
import org.jetbrains.anko.startActivity


class QuestionDetailActivity : BaseActivity() {


    private var isFavorite = false

    private lateinit var mQuestion: Question

    override fun getLayoutResId(): Int = R.layout.activity_question_detail


    override fun init() {
        super.init()
        val serialized = intent.getStringExtra(EXTRA_QUESTION)
        try {
            mQuestion = AVObject.parseAVObject(serialized) as Question
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mQuestionTitle.setText(mQuestion.title)
        if (mQuestion.description.isEmpty()) {
            mQuestionDescription.visibility = View.GONE
        } else {
            mQuestionDescription.visibility = View.VISIBLE
            mQuestionDescription.setText(mQuestion.description)
        }

        val currentUser = AVUser.getCurrentUser(User::class.java)
        isFavorite = currentUser.isFavouredQuestion(mQuestion.objectId)
        mFavourQuestion.isSelected = isFavorite

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mQuestionTitle.transitionName = getString(R.string.question_title_transition)
            mQuestionDescription.transitionName = getString(R.string.question_des_transition)
            val changeBounds = ChangeBounds()
            changeBounds.pathMotion = ArcMotion()
            window.sharedElementEnterTransition = changeBounds
        }

        setSupportActionBar(mToolBar)
        val supportActionBar = supportActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mCollapsingToolBar.setTitle(" ")

        mFavourQuestion.setOnClickListener {
            val currentUser = AVUser.getCurrentUser(User::class.java)
            isFavorite = !isFavorite
            mFavourQuestion.isSelected = isFavorite
            if (isFavorite) {
                UserQuestionFavourMap.buildFavourMap(currentUser, mQuestion)
            } else {
                UserQuestionFavourMap.breakFavourMap(currentUser, mQuestion)
            }
        }

        val titles = resources.getStringArray(R.array.answer_category)
        mViewPager.adapter = QuestionDetailPagerAdapter(supportFragmentManager, titles, mQuestion.objectId)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
        }
        return true
    }

    fun onAddAnswer(view: View) {
        startActivity<AddAnswerActivity>(EXTRA_QUESTION to mQuestion.toString())
    }


}
