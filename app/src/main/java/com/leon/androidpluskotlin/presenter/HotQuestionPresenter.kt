package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.HotQuestionContract
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.QuestionDataSource
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.di.ChildFragmentScope

import javax.inject.Inject

@ChildFragmentScope
class HotQuestionPresenter @Inject
constructor(private val mDataRepository: QuestionDataSource) : HotQuestionContract.Presenter {
    private var mView: HotQuestionContract.View? = null

    override fun takeView(view: HotQuestionContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun loadHotQuestions() {
        mDataRepository.getHotQuestionList(object : LoadCallback<Question> {
            override fun onLoadSuccess(list: List<Question>) {
                mView?.onLoadHotQuestionSuccess(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadHotQuestionFailed()

            }
        })
    }

    override fun loadMoreHotQuestions() {
        mDataRepository.getMoreHotQuestionList(object : LoadCallback<Question> {
            override fun onLoadSuccess(list: List<Question>) {
                mView?.onLoadMoreHotQuestionSuccess()

            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadMoreHotQuestionFailed()

            }
        })
    }
}
