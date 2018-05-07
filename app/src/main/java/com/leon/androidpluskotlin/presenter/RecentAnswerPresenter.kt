package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.RecentAnswerContract
import com.leon.androidpluskotlin.data.AnswerDataRepository
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.di.FragmentScoped

import javax.inject.Inject

@FragmentScoped
class RecentAnswerPresenter @Inject
internal constructor(private val mAnswerDataRepository: AnswerDataRepository) : RecentAnswerContract.Presenter {

    internal var mView: RecentAnswerContract.View? = null


    override fun takeView(view: RecentAnswerContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun loadRecentAnswerByQuestion(questionId: String) {
        mAnswerDataRepository.getRecentAnswerListByQuestion(questionId, object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                mView?.onLoadRecentAnswerSuccess(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadRecentAnswerFailed()
            }
        })
    }

    override fun loadMoreRecentAnswerByQuestion(questionId: String) {
        mAnswerDataRepository.getMoreRecentAnswerListByQuestion(questionId, object : LoadCallback<Answer> {
            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadMoreRecentAnswerFailed()
            }

            override fun onLoadSuccess(list: List<Answer>) {
                mView?.onLoadMoreRecentAnswerSuccess(list)
            }
        })
    }
}
