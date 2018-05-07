package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.leon.androidpluskotlin.app.DEFAULT_PAGE_SIZE

import com.leon.androidpluskotlin.widget.CardLoadingView

abstract class BaseLoadingListAdapter<T>(val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataList: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_NORMAL) {
            onCreateNormalViewHolder()
        } else {
            onCreateLoadingViewHolder()
        }
    }

    open fun onCreateLoadingViewHolder(): LoadingViewHolder {
        return LoadingViewHolder(CardLoadingView(context))
    }

    internal abstract fun onCreateNormalViewHolder(): RecyclerView.ViewHolder

    internal abstract fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            onBindNormalViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (dataList.size < DEFAULT_PAGE_SIZE) {
            return ITEM_TYPE_NORMAL
        }

        return if (position == itemCount - 1) {
            ITEM_TYPE_LOADING
        } else {
            ITEM_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return when {
            dataList.isEmpty() -> 0
            dataList.size < DEFAULT_PAGE_SIZE -> dataList.size
            else -> dataList.size + 1
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun replaceData(dataList: List<T>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    companion object {
        private val ITEM_TYPE_NORMAL = 0
        private val ITEM_TYPE_LOADING = 1
    }

}
