package com.leon.androidpluskotlin.ui.fragment

import android.os.Bundle
import com.leon.androidpluskotlin.app.EXTRA_QUESTION_ID
import com.leon.androidpluskotlin.contract.RecentAnswerContract
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.presenter.RecentAnswerPresenter
import kotlinx.android.synthetic.main.fragment_base_refreshable_list.*
import javax.inject.Inject

class RecentAnswerFragment : BaseAnswerFragment(), RecentAnswerContract.View {

    @Inject
    lateinit var mRecentAnswerPresenter: RecentAnswerPresenter

    lateinit var mQuestionId: String


    override fun init() {
        super.init()
        mRecentAnswerPresenter.takeView(this)
        mQuestionId = arguments.getString(EXTRA_QUESTION_ID)
        mRecentAnswerPresenter.loadRecentAnswerByQuestion(mQuestionId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecentAnswerPresenter.dropView()
    }

    override fun startRefresh() {
        mRecentAnswerPresenter.loadRecentAnswerByQuestion(mQuestionId!!)
    }

    override fun startLoadMoreData() {
        mRecentAnswerPresenter.loadMoreRecentAnswerByQuestion(mQuestionId!!)
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

    companion object {

        fun newInstance(questionId: String): RecentAnswerFragment {
            val recentAnswerFragment = RecentAnswerFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_QUESTION_ID, questionId)
            recentAnswerFragment.arguments = bundle
            return recentAnswerFragment

        }
    }
}
