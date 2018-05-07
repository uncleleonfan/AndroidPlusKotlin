package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.AddAnswerContract
import com.leon.androidpluskotlin.data.AnswerDataSource
import com.leon.androidpluskotlin.data.SaveCallback
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.di.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class AddAnswerPresenter @Inject
constructor(private val mAnswerDataRepository: AnswerDataSource) : AddAnswerContract.Presenter {

    internal var mView: AddAnswerContract.View? = null


    private val mSaveCallback = object : SaveCallback {
        override fun onSaveSuccess() {
            mView?.onPublishAnswerSuccess()
        }

        override fun onSaveFailed(errorMsg: String) {
            mView?.onPublishAnswerSuccess()
        }
    }

    override fun takeView(view: AddAnswerContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun publishAnswer(answer: String, question: Question) {
        mAnswerDataRepository.addAnswerToQuestion(answer, question, mSaveCallback)
    }
}
