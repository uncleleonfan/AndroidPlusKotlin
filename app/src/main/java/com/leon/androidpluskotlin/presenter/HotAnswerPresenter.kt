package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.HotAnswerContract
import com.leon.androidpluskotlin.data.AnswerDataRepository
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.di.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class HotAnswerPresenter @Inject
constructor(private val mDataRepository: AnswerDataRepository) : HotAnswerContract.Presenter {


    private var mView: HotAnswerContract.View? = null


    override fun takeView(view: HotAnswerContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun loadHotAnswerByQuestion(questionId: String) {
        mDataRepository.getHotAnswerListByQuestion(questionId, object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                mView?.onLoadHotAnswerSuccess(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadHotAnswerFailed()
            }
        })
    }

    override fun loadMoreHotAnswerByQuestion(questionId: String) {
        mDataRepository.getMoreHotAnswerListByQuestion(questionId, object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                mView?.onLoadMoreHotAnswerSuccess(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadMoreHotAnswerFailed()
            }
        })
    }
}
