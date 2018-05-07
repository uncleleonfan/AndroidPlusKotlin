package com.leon.androidpluskotlin.widget

import android.app.Activity
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_USER_ID
import com.leon.androidpluskotlin.app.GlideApp
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.ui.activity.ProfileActivity
import com.leon.androidpluskotlin.utils.getDisplayString
import com.leon.androidpluskotlin.utils.transitionToQuestionDetail
import kotlinx.android.synthetic.main.view_question_item.view.*
import org.jetbrains.anko.startActivity


class QuestionItemView(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    private lateinit var mQuestion: Question

    init {
        LayoutInflater.from(context).inflate(R.layout.view_question_item, this)
        val layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.default_margin)
        layoutParams.setMargins(0, margin, 0, 0)
        setLayoutParams(layoutParams)

    }

    fun bindView(question: Question) {
        mQuestion = question
        mUserName.text = question.user.username
        mQuestionTitle.text = question.title
        if (question.description.isEmpty()) {
            mQuestionDescription.visibility = View.GONE
        } else {
            mQuestionDescription.visibility = View.VISIBLE
            mQuestionDescription.text = question.description
        }
        mAnswerCount.text = question.answerCount.toString()
        mFavourCount.text = question.favourCount.toString()
        mPublishDate.text = getDisplayString(context, question.createdAt)
        GlideApp.with(this)
                .load(question.user.avatar)
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(CircleCrop())
                .into(mAvatar)

        mUserName.setOnClickListener { goToProfile() }
        mAvatar.setOnClickListener { goToProfile() }
        mQuestionItem.setOnClickListener {
            transitionToQuestionDetail(context as Activity, mQuestion, mQuestionTitle, mQuestionDescription)
        }
    }

    private fun goToProfile() {
        context.startActivity<ProfileActivity>(EXTRA_USER_ID to mQuestion.user.objectId)
    }


}
