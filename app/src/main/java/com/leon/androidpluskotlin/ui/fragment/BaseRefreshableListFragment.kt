package com.leon.androidpluskotlin.ui.fragment



import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.BaseLoadingListAdapter
import com.leon.androidpluskotlin.event.ScrollEvent
import kotlinx.android.synthetic.main.fragment_base_refreshable_list.*
import org.greenrobot.eventbus.EventBus

abstract class BaseRefreshableListFragment<T> : BaseFragment() {


    private var mScrollState = 0

    lateinit var adapter: BaseLoadingListAdapter<T>

    override fun getLayoutResId(): Int = R.layout.fragment_base_refreshable_list


    override fun init() {
        super.init()
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = onCreateAdapter()
        mRecyclerView.adapter = adapter
        mRecyclerView.addOnScrollListener(mOnScrollListener)

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener)
        mSwipeRefreshLayout.isRefreshing = true

        mError.setOnClickListener {
            mError.visibility = View.GONE
            mSwipeRefreshLayout.isRefreshing = true
            startRefresh()
        }

    }

    private val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { startRefresh() }

    private val mOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (isEnableScrollEvent() && dy != 0) {
                postScrollEvent(dy)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            if (mRecyclerView == null || mRecyclerView.adapter.itemCount == 0) {
                return
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && shouldLoadMore()) {
                startLoadMoreData()
            }
        }
    }

    open fun isEnableScrollEvent(): Boolean {
        return true
    }


    protected abstract fun onCreateAdapter(): BaseLoadingListAdapter<T>

    protected abstract fun startRefresh()

    private fun shouldLoadMore(): Boolean {
        val layoutManager = mRecyclerView.getLayoutManager() as LinearLayoutManager
        return layoutManager.findLastVisibleItemPosition() == mRecyclerView.getAdapter().getItemCount() - 1
    }

    protected abstract fun startLoadMoreData()

    private fun postScrollEvent(dy: Int) {
        if (dy > 0) {
            if (mScrollState != SCROLL_UP) {
                EventBus.getDefault().post(ScrollEvent(ScrollEvent.Direction.UP))
                mScrollState = SCROLL_UP
            }
        } else {
            if (mScrollState != SCROLL_DOWN) {
                EventBus.getDefault().post(ScrollEvent(ScrollEvent.Direction.DOWN))
                mScrollState = SCROLL_DOWN
            }
        }
    }


    companion object {

        private val SCROLL_UP = 1
        private val SCROLL_DOWN = 2
    }
}
