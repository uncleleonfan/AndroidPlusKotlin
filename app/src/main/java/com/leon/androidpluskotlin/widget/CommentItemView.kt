package com.leon.androidpluskotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.GlideApp
import com.leon.androidpluskotlin.data.model.Comment
import com.leon.androidpluskotlin.utils.getDisplayString
import kotlinx.android.synthetic.main.view_comment_item.view.*


class CommentItemView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_comment_item, this)
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        setPadding(padding, padding, padding, padding)
    }


    fun bindView(comment: Comment) {
        mUserName.setText(comment.user.username)
        mComment.setText(comment.content)
        GlideApp.with(this).load(comment.user.avatar)
                .transform(CircleCrop())
                .placeholder(R.mipmap.ic_launcher_round)
                .transition(DrawableTransitionOptions().crossFade())
                .into(mAvatar)
        mPublishDate.text = getDisplayString(context, comment.createdAt)
    }
}
