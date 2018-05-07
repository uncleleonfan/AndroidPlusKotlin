package com.leon.androidpluskotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.utils.getDisplayString
import kotlinx.android.synthetic.main.view_user_question_item.view.*


class UserQuestionItemView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_user_question_item, this)
    }

    fun bindView(question: Question) {
        mQuestionTitle.setText(question.title)
        if (question.description.isEmpty()) {
            mQuestionDescription.visibility = View.GONE
        } else {
            mQuestionDescription.visibility = View.VISIBLE
            mQuestionDescription.text = question.description
        }
        mAnswerCount.text = question.answerCount.toString()
        mFavourCount.text = question.favourCount.toString()
        mPublishDate.text = getDisplayString(context, question.createdAt)
    }
}
