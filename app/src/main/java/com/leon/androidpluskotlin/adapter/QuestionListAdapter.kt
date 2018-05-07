package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.widget.QuestionItemView

class QuestionListAdapter(context: Context) : BaseLoadingListAdapter<Question>(context) {

    override fun onCreateNormalViewHolder(): RecyclerView.ViewHolder {
        return QuestionItemViewHolder(QuestionItemView(context))
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as QuestionItemView).bindView(dataList[position])

    }

    private class QuestionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
