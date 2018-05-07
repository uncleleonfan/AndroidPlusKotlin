package com.leon.androidpluskotlin.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_ANSWER
import com.leon.androidpluskotlin.app.EXTRA_USER_ID
import com.leon.androidpluskotlin.app.GlideApp
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.ui.activity.AnswerDetailActivity
import com.leon.androidpluskotlin.ui.activity.ProfileActivity
import com.leon.androidpluskotlin.utils.getDisplayString
import kotlinx.android.synthetic.main.view_answer_item.view.*
import org.jetbrains.anko.startActivity


class AnswerItemView(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {


    private lateinit var mAnswer: Answer

    init {
        LayoutInflater.from(context).inflate(R.layout.view_answer_item, this)
        val layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.default_margin)
        layoutParams.setMargins(0, margin, 0, 0)
        setLayoutParams(layoutParams)

        mUserName.setOnClickListener { goToProfile() }
        mAvatar.setOnClickListener { goToProfile() }
        mAnswerContainer.setOnClickListener {
            getContext().startActivity<AnswerDetailActivity>(EXTRA_ANSWER to mAnswer.toString())
        }

    }

    private fun goToProfile() {
        context.startActivity<ProfileActivity>(EXTRA_USER_ID to mAnswer.user.objectId)
    }

    fun bindView(answer: Answer) {
        mAnswer = answer
        mAnswerText.text = answer.content
        mUserName.text = answer.user.getUsername()
        mFavourCount.text = answer.likeCount.toString()
        mCommentCount.text = answer.commentCount.toString()
        mPublishDate.text = getDisplayString(context, answer.createdAt)
        GlideApp.with(this)
                .load(answer.user.avatar)
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(CircleCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .into(mAvatar)
    }


}
