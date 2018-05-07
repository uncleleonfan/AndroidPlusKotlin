package com.leon.androidpluskotlin.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * Created by Leon on 2017-10-18.
 */

abstract class BaseListAdapter<T>(val context: Context) : BaseAdapter() {

    var dataList = mutableListOf<T>()

    override fun getCount(): Int {
         return dataList.size
    }

    /**
     * 返回对应位置的数据
     * @param position
     * @return
     */
    override fun getItem(position: Int): T {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var vh: ViewHolder? = null
        if (convertView == null) {
            val view = onCreateItemView(position)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            vh = convertView.tag as ViewHolder
        }
        onBindItemView(vh.mItemView, position)
        return convertView
    }

    fun replaceData(dataList: List<T>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    class ViewHolder(val mItemView: View)

    /**
     * 子类必须实现该方法来完成条目的创建
     * @return
     */
    internal abstract fun onCreateItemView(position: Int): View

    /**
     * 子类实现该方法来完成条目的绑定
     * @param itemView
     * @param position
     */
    internal abstract fun onBindItemView(itemView: View, position: Int)
}
