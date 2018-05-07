package com.leon.androidpluskotlin.presenter

import com.leon.androidpluskotlin.contract.AddQuestionContract
import com.leon.androidpluskotlin.data.QuestionDataSource
import com.leon.androidpluskotlin.data.SaveCallback
import com.leon.androidpluskotlin.di.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class AddQuestionPresenter @Inject
constructor(private val mQuestionDataSource: QuestionDataSource) : AddQuestionContract.Presenter {

    private var mView: AddQuestionContract.View? = null

    override fun takeView(view: AddQuestionContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun publishQuestion(title: String, des: String) {
        mQuestionDataSource.addQuestion(title, des, object : SaveCallback {
            override fun onSaveSuccess() {
                mView!!.onPublishSuccess()
            }

            override fun onSaveFailed(errorMsg: String) {
                mView!!.onPublishFailed()
            }
        })
    }
}
