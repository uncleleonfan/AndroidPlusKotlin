package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.RecentQuestionContract
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.QuestionDataSource
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.di.ChildFragmentScope

import javax.inject.Inject

@ChildFragmentScope
class RecentQuestionPresenter @Inject
constructor(private val mDataRepository: QuestionDataSource) : RecentQuestionContract.Presenter {

    private var mView: RecentQuestionContract.View? = null

    override fun takeView(view: RecentQuestionContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun loadRecentQuestions() {
        mDataRepository.getRecentQuestionList(object : LoadCallback<Question> {
            override fun onLoadSuccess(list: List<Question>) {
                    mView?.onLoadRecentQuestionSuccess(list)

            }

            override fun onLoadFailed(errorMsg: String) {
                    mView?.onLoadRecentQuestionFailed()

            }
        })
    }

    override fun loadMoreRecentQuestions() {
        mDataRepository.getMoreRecentQuestionList(object : LoadCallback<Question> {
            override fun onLoadSuccess(list: List<Question>) {
                mView?.onLoadMoreRecentQuestionSuccess()
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadMoreRecentQuestionFailed()
            }
        })
    }
}
