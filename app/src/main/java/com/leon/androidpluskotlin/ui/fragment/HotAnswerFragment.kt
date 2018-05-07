package com.leon.androidpluskotlin.ui.fragment

import android.os.Bundle
import com.leon.androidpluskotlin.app.EXTRA_QUESTION_ID

import com.leon.androidpluskotlin.contract.HotAnswerContract
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.presenter.HotAnswerPresenter
import kotlinx.android.synthetic.main.fragment_base_refreshable_list.*

import javax.inject.Inject

class HotAnswerFragment : BaseAnswerFragment(), HotAnswerContract.View {

    @Inject
    lateinit var mHotAnswerPresenter: HotAnswerPresenter

    private lateinit var mQuestionId: String

    override fun init() {
        super.init()
        mHotAnswerPresenter.takeView(this)
        mQuestionId = arguments.getString(EXTRA_QUESTION_ID)
        mHotAnswerPresenter.loadHotAnswerByQuestion(mQuestionId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mHotAnswerPresenter.dropView()
    }

    override fun startRefresh() {
        mHotAnswerPresenter.loadHotAnswerByQuestion(mQuestionId)
    }

    override fun startLoadMoreData() {
        mHotAnswerPresenter.loadMoreHotAnswerByQuestion(mQuestionId)
    }

    override fun onLoadHotAnswerSuccess(list: List<Answer>) {
        mSwipeRefreshLayout.isRefreshing = false
        adapter.replaceData(list)
    }

    override fun onLoadHotAnswerFailed() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun onLoadMoreHotAnswerSuccess(list: List<Answer>) {
        adapter.notifyDataSetChanged()
    }

    override fun onLoadMoreHotAnswerFailed() {

    }

    companion object {

        fun newInstance(questionId: String): HotAnswerFragment {
            val hotAnswerFragment = HotAnswerFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_QUESTION_ID, questionId)
            hotAnswerFragment.arguments = bundle
            return hotAnswerFragment

        }
    }
}
