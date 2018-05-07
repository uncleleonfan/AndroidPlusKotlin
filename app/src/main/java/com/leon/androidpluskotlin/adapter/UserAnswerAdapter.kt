package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.view.View

import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.widget.UserAnswerItemView

class UserAnswerAdapter(context: Context) : BaseListAdapter<Answer>(context) {

    override fun onCreateItemView(position: Int): View {
        return UserAnswerItemView(context)
    }

    override fun onBindItemView(itemView: View, position: Int) {
        (itemView as UserAnswerItemView).bindView(dataList[position])
    }
}
