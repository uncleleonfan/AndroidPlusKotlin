package com.leon.androidpluskotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.utils.getDisplayString
import kotlinx.android.synthetic.main.view_user_answer_item.view.*

class UserAnswerItemView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_user_answer_item, this)
    }

    fun bindView(answer: Answer) {
        mQuestionTitle.text = answer.question.title
        mAnswer.text = answer.content
        val createAt = answer.createdAt
        mFavourCount.setText(answer.likeCount.toString())
        mCommentCount.setText(answer.commentCount.toString())
        mPublishDate.text = getDisplayString(context, createAt)
    }
}
