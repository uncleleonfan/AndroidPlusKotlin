package com.leon.androidpluskotlin.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater

import com.leon.androidpluskotlin.R


class CardLoadingView(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.card_view_loading, this)
        val layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.default_margin)
        layoutParams.setMargins(0, margin, 0, margin)
        setLayoutParams(layoutParams)
    }


}
