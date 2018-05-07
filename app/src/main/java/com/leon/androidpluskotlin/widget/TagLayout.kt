package com.leon.androidpluskotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View.OnClickListener
import android.widget.TextView
import com.leon.androidpluskotlin.R


class TagLayout(context: Context, attrs: AttributeSet? = null): FlowLayout(context) {

    private var mOnTagSelectedListener: OnTagSelectedListener? = null

    private var mLastSelectedPosition = -1



    fun setTags(tags: Array<String>) {
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        for (i in tags.indices) {
            val textView = TextView(context)
            textView.text = tags[i]
            textView.setBackgroundResource(R.drawable.category_item_selector)
            textView.setPadding(padding, padding, padding, padding)
            textView.text = tags[i]
            textView.gravity = Gravity.CENTER
            textView.setTextColor(resources.getColorStateList(R.color.category_item_color))
            textView.setOnClickListener(OnClickListener { v ->
                if (i == mLastSelectedPosition) {
                    return@OnClickListener
                }
                v.isSelected = true
                if (mLastSelectedPosition > -1) {
                    getChildAt(mLastSelectedPosition).isSelected = false
                }
                if (mOnTagSelectedListener != null) {
                    mOnTagSelectedListener!!.onTagSelected(tags[i], i)
                }
                mLastSelectedPosition = i
            })
            addView(textView)
        }
    }

    interface OnTagSelectedListener {
        fun onTagSelected(tag: String, position: Int)
    }

    fun setOnTagSelectedListener(listener: OnTagSelectedListener) {
        mOnTagSelectedListener = listener
    }
}
