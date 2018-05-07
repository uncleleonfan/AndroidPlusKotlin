package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.CommentContract
import com.leon.androidpluskotlin.data.CommentDataSource
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.SaveCallback
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Comment
import com.leon.androidpluskotlin.di.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class CommentPresenter @Inject
constructor(private val mDataRepository: CommentDataSource) : CommentContract.Presenter {

    private var mView: CommentContract.View? = null

    override fun takeView(view: CommentContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun loadComments(answerId: String) {
        mDataRepository.loadComments(answerId, object : LoadCallback<Comment> {
            override fun onLoadSuccess(list: List<Comment>) {
                mView?.onLoadCommentsSuccess(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadCommentFailed()
            }
        })
    }

    override fun sendComment(answer: Answer, replayTo: String?, comment: String) {
        mDataRepository.sendComment(answer, replayTo, comment, object : SaveCallback {
            override fun onSaveSuccess() {
                mView?.onSendCommentSuccess()
            }

            override fun onSaveFailed(errorMsg: String) {
                mView?.onSendCommentFailed()
            }
        })
    }

    override fun loadMoreComments(answerId: String) {
        mDataRepository.loadMoreComments(answerId, object : LoadCallback<Comment> {
            override fun onLoadSuccess(list: List<Comment>) {
                mView?.onLoadMoreCommentsSuccess()
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadMoreCommentFailed()
            }
        })
    }
}
