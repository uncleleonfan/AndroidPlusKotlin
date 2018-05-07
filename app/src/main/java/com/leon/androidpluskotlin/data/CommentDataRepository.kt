package com.leon.androidpluskotlin.data

import com.avos.avoscloud.*
import com.leon.androidpluskotlin.app.DEFAULT_PAGE_SIZE
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Comment
import com.leon.androidpluskotlin.data.model.User
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentDataRepository @Inject
constructor() : CommentDataSource {

    private lateinit var mCommentsCache: MutableList<Comment>

    private val lastCommentCrateAt: Date
        get() = mCommentsCache[mCommentsCache.size - 1].createdAt

    override fun sendComment(answer: Answer, replayTo: String?, commentString: String, callback: SaveCallback) {
        val comment = Comment()
        val user = AVUser.getCurrentUser(User::class.java)
        comment.content = commentString
        answer.addCommentCount()
        comment.answer = answer
        comment.user = user
        comment.saveInBackground(object : com.avos.avoscloud.SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    callback.onSaveSuccess()
                } else {
                    callback.onSaveFailed(e.localizedMessage)
                }
            }
        })
    }

    override fun loadComments(answerId: String, callback: LoadCallback<Comment>) {
        loadCommentsFromServer(answerId, callback, false)
    }

    private fun loadCommentsFromServer(answerId: String, loadCallback: LoadCallback<Comment>, isLoadMore: Boolean) {
        val commentAVQuery = AVQuery.getQuery(Comment::class.java)
        try {
            val answer = AVObject.createWithoutData<Answer>(Answer::class.java, answerId)
            commentAVQuery.whereEqualTo(Comment.ANSWER, answer)
                    .include(Comment.USER)
                    .selectKeys(Arrays.asList(Comment.USER_NAME, Comment.USER_AVATAR, Comment.CONTENT))
                    .orderByDescending(AVObject.CREATED_AT)
                    .limit(DEFAULT_PAGE_SIZE)
            if (isLoadMore) {
                commentAVQuery.whereLessThan(AVObject.CREATED_AT, lastCommentCrateAt)
            }
            commentAVQuery.findInBackground(object : FindCallback<Comment>() {
                override fun done(list: MutableList<Comment>, e: AVException?) {
                    if (e == null) {
                        if (isLoadMore) {
                            mCommentsCache.addAll(list)
                        } else {
                            mCommentsCache = list
                        }
                        loadCallback.onLoadSuccess(list)
                    } else {
                        loadCallback.onLoadFailed(e.localizedMessage)
                    }
                }
            })
        } catch (e: AVException) {
            e.printStackTrace()
        }

    }

    override fun loadMoreComments(answerId: String, loadCallback: LoadCallback<Comment>) {
        loadCommentsFromServer(answerId, loadCallback, true)
    }
}
