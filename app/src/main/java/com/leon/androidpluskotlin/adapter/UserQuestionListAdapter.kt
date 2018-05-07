package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.view.View

import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.widget.UserQuestionItemView

class UserQuestionListAdapter(context: Context) : BaseListAdapter<Question>(context) {

    override fun onCreateItemView(position: Int): View {
        return UserQuestionItemView(context)
    }

    override fun onBindItemView(itemView: View, position: Int) {
        (itemView as UserQuestionItemView).bindView(dataList[position])
    }
}
