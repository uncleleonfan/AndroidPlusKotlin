package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.leon.androidpluskotlin.data.model.Comment

import com.leon.androidpluskotlin.widget.CommentItemView
import com.leon.androidpluskotlin.widget.LoadingView

class CommentListAdapter(context: Context) : BaseLoadingListAdapter<Comment>(context) {

    override fun onCreateNormalViewHolder(): RecyclerView.ViewHolder {
        return CommentListItemViewHolder(CommentItemView(context))
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as CommentItemView).bindView(dataList[position])
    }

    class CommentListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateLoadingViewHolder(): BaseLoadingListAdapter.LoadingViewHolder {
        return BaseLoadingListAdapter.LoadingViewHolder(LoadingView(context))
    }
}
