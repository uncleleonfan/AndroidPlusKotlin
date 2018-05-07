package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.view.View

import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.widget.UserShareArticleItemView

class UserArticleListAdapter(context: Context) : BaseListAdapter<Article>(context) {

    override fun onCreateItemView(position: Int): View {
        return UserShareArticleItemView(context)
    }

    override fun onBindItemView(itemView: View, position: Int) {
        (itemView as UserShareArticleItemView).bindView(dataList[position])
    }
}
