package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.widget.ArticleItemView

class ArticleListAdapter constructor(context: Context) : BaseLoadingListAdapter<Article>(context) {

    override fun onCreateNormalViewHolder(): RecyclerView.ViewHolder {
        return ArticleItemViewHolder(ArticleItemView(context))
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as ArticleItemView).bindView(dataList[position])
    }

    class ArticleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
