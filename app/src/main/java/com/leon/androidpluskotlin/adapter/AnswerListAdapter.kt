package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.widget.AnswerItemView

class AnswerListAdapter constructor(context: Context) : BaseLoadingListAdapter<Answer>(context) {

    override fun onCreateNormalViewHolder(): RecyclerView.ViewHolder {
        return AnswerItemViewHolder(AnswerItemView(context))
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as AnswerItemView).bindView(dataList[position])
    }

    private class AnswerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}
