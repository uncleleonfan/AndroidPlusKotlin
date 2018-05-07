package com.leon.androidpluskotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.utils.getDisplayString
import kotlinx.android.synthetic.main.view_user_share_item.view.*


class UserShareArticleItemView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_user_share_item, this)
    }

    fun bindView(article: Article) {
        mArticleTitle.text = article.title
        if (article.des.isEmpty()) {
            mArticleDescription.visibility = View.GONE
        } else {
            mArticleDescription.visibility = View.VISIBLE
            mArticleDescription.text = article.des
        }
        mFavourCount.text = article.favourCount.toString()
        mPublishDate.text = getDisplayString(context, article.createdAt)
    }
}
