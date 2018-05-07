package com.leon.androidpluskotlin.ui.fragment


import com.leon.androidpluskotlin.adapter.BaseLoadingListAdapter
import com.leon.androidpluskotlin.adapter.QuestionListAdapter
import com.leon.androidpluskotlin.contract.HotQuestionContract
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.presenter.HotQuestionPresenter
import kotlinx.android.synthetic.main.fragment_base_refreshable_list.*

import javax.inject.Inject

class HotQuestionFragment : BaseRefreshableListFragment<Question>(), HotQuestionContract.View {

    @Inject
    lateinit var mHotQuestionPresenter: HotQuestionPresenter

    public override fun onCreateAdapter(): BaseLoadingListAdapter<Question> {
        return QuestionListAdapter(context)
    }

    override fun init() {
        super.init()
        mHotQuestionPresenter.takeView(this)
        mSwipeRefreshLayout.isRefreshing = true
        mHotQuestionPresenter.loadHotQuestions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mHotQuestionPresenter.dropView()
    }

    override fun startRefresh() {
        mHotQuestionPresenter.loadHotQuestions()
    }

    override fun startLoadMoreData() {
        mHotQuestionPresenter.loadMoreHotQuestions()
    }

    override fun onLoadHotQuestionSuccess(list: List<Question>) {
        mSwipeRefreshLayout.isRefreshing = false
        adapter.replaceData(list)
    }

    override fun onLoadHotQuestionFailed() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun onLoadMoreHotQuestionSuccess() {
        adapter.notifyDataSetChanged()
    }

    override fun onLoadMoreHotQuestionFailed() {

    }
}
