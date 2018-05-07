package com.leon.androidpluskotlin.ui.fragment

import android.view.View

import com.leon.androidpluskotlin.adapter.BaseLoadingListAdapter
import com.leon.androidpluskotlin.adapter.QuestionListAdapter
import com.leon.androidpluskotlin.contract.RecentQuestionContract
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.presenter.RecentQuestionPresenter
import kotlinx.android.synthetic.main.fragment_base_refreshable_list.*

import javax.inject.Inject

class QuestionFragment : BaseRefreshableListFragment<Question>(), RecentQuestionContract.View {

    @Inject
    lateinit var mQuestionPresenter: RecentQuestionPresenter


    public override fun onCreateAdapter(): BaseLoadingListAdapter<Question> {
        return QuestionListAdapter(context)
    }

    override fun init() {
        super.init()
        mQuestionPresenter.takeView(this)
        mQuestionPresenter.loadRecentQuestions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mQuestionPresenter.dropView()
    }

    override fun onLoadRecentQuestionSuccess(list: List<Question>) {
        mError.visibility = View.GONE
        mSwipeRefreshLayout.isRefreshing = false
        adapter.replaceData(list)
    }

    override fun onLoadRecentQuestionFailed() {
        mSwipeRefreshLayout.isRefreshing = false
        mError.visibility = View.VISIBLE
    }


    override fun startRefresh() {
        mQuestionPresenter.loadRecentQuestions()
    }

    override fun startLoadMoreData() {
        mQuestionPresenter.loadMoreRecentQuestions()
    }

    override fun onLoadMoreRecentQuestionFailed() {
    }

    override fun onLoadMoreRecentQuestionSuccess() {
        adapter.notifyDataSetChanged()
    }

}
