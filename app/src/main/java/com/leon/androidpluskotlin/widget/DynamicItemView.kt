package com.leon.androidpluskotlin.widget

import android.app.Activity
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater

import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.utils.getDisplayString
import com.leon.androidpluskotlin.utils.transitionToAnswerDetail
import kotlinx.android.synthetic.main.view_dynamic_item.view.*


class DynamicItemView(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    private lateinit var mAnswer: Answer

    init {
        LayoutInflater.from(context).inflate(R.layout.view_dynamic_item, this)
        val layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.default_margin)
        layoutParams.setMargins(0, margin, 0, 0)
        setLayoutParams(layoutParams)

        mDynamicItem.setOnClickListener {
            transitionToAnswerDetail(getContext() as Activity, mAnswer, mQuestionTitle, mAnswerText)

        }
    }

    fun bindView(answer: Answer) {
        mAnswer = answer
        mQuestionTitle.text = answer.question.title
        val answerString = String.format(resources.getString(R.string.dynamic_user_answer),
                answer.user.username,
                answer.content)
        mAnswerText.text = answerString
        mCommentCount.text = answer.commentCount.toString()
        mFavourCount.text = answer.likeCount.toString()
        mPublishDate.text = getDisplayString(context, answer.createdAt)
    }


}
