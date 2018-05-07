package com.leon.androidpluskotlin.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_ARTICLE
import com.leon.androidpluskotlin.app.EXTRA_USER_ID
import com.leon.androidpluskotlin.app.GlideApp
import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.ui.activity.ArticleDetailActivity
import com.leon.androidpluskotlin.ui.activity.ProfileActivity
import com.leon.androidpluskotlin.utils.getDisplayString
import kotlinx.android.synthetic.main.view_article_item.view.*
import org.jetbrains.anko.startActivity


class ArticleItemView(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {


    private lateinit var mArticle: Article

    init {
        LayoutInflater.from(context).inflate(R.layout.view_article_item, this)
        val layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.default_margin)
        layoutParams.setMargins(0, margin, 0, 0)
        setLayoutParams(layoutParams)

        mAvatar.setOnClickListener { goToProfile() }
        mUserName.setOnClickListener { goToProfile() }
        mArticleItem.setOnClickListener {
            context.startActivity<ArticleDetailActivity>(EXTRA_ARTICLE to mArticle.toString() )
        }
    }

    private fun goToProfile() {
        context.startActivity<ProfileActivity>(EXTRA_USER_ID to mArticle.user.objectId)
    }

    fun bindView(article: Article) {
        mArticle = article
        mUserName.text = article.user.username
        mArticleTitle.text = article.title
        if (article.des.isEmpty()) {
            mArticleDescription.visibility = View.GONE
        } else {
            mArticleDescription.visibility = View.VISIBLE
            mArticleDescription.text = article.des
        }
        mPublishDate.text = getDisplayString(context, article.createdAt)
        mFavourCount.text = article.favourCount.toString()
        GlideApp.with(this).load(article.user.avatar)
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(CircleCrop()).transition(DrawableTransitionOptions().crossFade()).into(mAvatar)
    }

}
