package com.leon.androidpluskotlin.ui.fragment


import com.leon.androidpluskotlin.adapter.BaseLoadingListAdapter
import com.leon.androidpluskotlin.adapter.DynamicListAdapter
import com.leon.androidpluskotlin.contract.DynamicContract
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.presenter.DynamicPresenter
import kotlinx.android.synthetic.main.fragment_base_refreshable_list.*

import javax.inject.Inject

class DynamicFragment : BaseRefreshableListFragment<Answer>(), DynamicContract.View {


    @Inject
    lateinit var mDynamicPresenter: DynamicPresenter


    public override fun onCreateAdapter(): BaseLoadingListAdapter<Answer> {
        return DynamicListAdapter(context)
    }

    override fun init() {
        super.init()
        mDynamicPresenter.takeView(this)
        mDynamicPresenter.loadRecentAnswer()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mDynamicPresenter.dropView()
    }

    override fun startRefresh() {
        mDynamicPresenter.loadRecentAnswer()
    }

    override fun startLoadMoreData() {
        mDynamicPresenter.loadMoreRecentAnswer()
    }

    override fun onLoadRecentAnswerSuccess(list: List<Answer>) {
        mSwipeRefreshLayout.isRefreshing = false
        adapter.replaceData(list)
    }

    override fun onLoadRecentAnswerFailed() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun onLoadMoreRecentAnswerSuccess(list: List<Answer>) {
        adapter.notifyDataSetChanged()
    }

    override fun onLoadMoreRecentAnswerFailed() {

    }
}
