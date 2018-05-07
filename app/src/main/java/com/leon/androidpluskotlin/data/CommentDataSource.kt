package com.leon.androidpluskotlin.data


import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Comment

interface CommentDataSource {

    fun sendComment(answer: Answer, replayTo: String?, commentString: String, callback: SaveCallback)

    fun loadComments(answerId: String, callback: LoadCallback<Comment>)

    fun loadMoreComments(answerId: String, loadCallback: LoadCallback<Comment>)
}
