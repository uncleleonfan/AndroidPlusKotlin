package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.DynamicContract
import com.leon.androidpluskotlin.data.AnswerDataSource
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.di.ChildFragmentScope

import javax.inject.Inject

@ChildFragmentScope
class DynamicPresenter @Inject
constructor(private val mAnswerDataSource: AnswerDataSource) : DynamicContract.Presenter {

    private var mView: DynamicContract.View? = null

    override fun takeView(view: DynamicContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun loadRecentAnswer() {
        mAnswerDataSource.getRecentAnswerList(object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                mView?.onLoadRecentAnswerSuccess(list)

            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadRecentAnswerFailed()

            }
        })
    }

    override fun loadMoreRecentAnswer() {
        mAnswerDataSource.getMoreRecentAnswerList(object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                mView?.onLoadMoreRecentAnswerSuccess(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadMoreRecentAnswerFailed()
            }
        })
    }
}