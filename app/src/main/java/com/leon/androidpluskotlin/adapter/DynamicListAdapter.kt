package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.widget.DynamicItemView

class DynamicListAdapter constructor(context: Context) : BaseLoadingListAdapter<Answer>(context) {

    override fun onCreateNormalViewHolder(): RecyclerView.ViewHolder {
        return DynamicItemViewHolder(DynamicItemView(context))
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as DynamicItemView).bindView(dataList[position])
    }

    class DynamicItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
