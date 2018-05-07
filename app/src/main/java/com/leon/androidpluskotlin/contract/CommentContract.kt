package com.leon.androidpluskotlin.contract


import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Comment

interface CommentContract {

    interface View : BaseView {

        fun onSendCommentSuccess()
        fun onSendCommentFailed()

        fun onLoadCommentsSuccess(comments: List<Comment>)
        fun onLoadCommentFailed()

        fun onLoadMoreCommentsSuccess()
        fun onLoadMoreCommentFailed()

    }

    interface Presenter : BasePresenter<View> {

        fun loadComments(answerId: String)

        fun sendComment(answer: Answer, replayTo: String?, comment: String)

        fun loadMoreComments(answerId: String)
    }
}
