package com.leon.androidpluskotlin.ui.activity

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.BaseListAdapter
import com.leon.androidpluskotlin.app.DEFAULT_PAGE_SIZE
import kotlinx.android.synthetic.main.activity_base_list_view.*


abstract class BaseListViewActivity<T> : BaseActivity() {


    lateinit var adapter: BaseListAdapter<T>


    override fun getLayoutResId(): Int {
        return R.layout.activity_base_list_view
    }


    override fun init() {
        super.init()
        val supportActionBar = supportActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initListView()
        initSwipeRefreshLayout()
        startLoadData()
    }


    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.isRefreshing = true
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener)
    }

    private fun initListView() {
        adapter = onCreateAdapter()
        mListView.adapter = adapter
        mListView.setOnScrollListener(mOnScrollListener)
        mListView.onItemClickListener = mOnItemClickListener
    }

    private val mOnItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> onListItemClick(view, position) }

    private val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { startRefresh() }

    private val mOnScrollListener = object : AbsListView.OnScrollListener {
        override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (mListView.lastVisiblePosition == mListView.adapter.count - 1) {
                    if (mListView.adapter.count >= DEFAULT_PAGE_SIZE) {
                        startLoadMoreData()
                    }
                }
            }
        }

        override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

        }
    }
    protected open fun onListItemClick(view: View, position: Int) {}

    protected abstract fun startRefresh()

    protected abstract fun startLoadMoreData()

    protected abstract fun startLoadData()

    internal abstract fun onCreateAdapter(): BaseListAdapter<T>

}
