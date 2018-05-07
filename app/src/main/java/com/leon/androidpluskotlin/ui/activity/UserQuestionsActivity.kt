package com.leon.androidpluskotlin.ui.activity

import android.view.View

import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.BaseListAdapter
import com.leon.androidpluskotlin.adapter.UserQuestionListAdapter
import com.leon.androidpluskotlin.app.EXTRA_USER_ID
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.QuestionDataSource
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.utils.transitionToQuestionDetail
import kotlinx.android.synthetic.main.activity_base_list_view.*

import javax.inject.Inject

class UserQuestionsActivity : BaseListViewActivity<Question>() {

    @Inject
    lateinit var mQuestionDataSource: QuestionDataSource

    private var mUserId: String? = null

    override fun startRefresh() {
        mQuestionDataSource.refreshUserQuestions(mUserId!!, object : LoadCallback<Question> {
            override fun onLoadFailed(errorMsg: String) {
                mSwipeRefreshLayout.isRefreshing = false
            }

            override fun onLoadSuccess(list: List<Question>) {
                mSwipeRefreshLayout.isRefreshing = false
                adapter.replaceData(list)
            }
        })
    }

    override fun startLoadMoreData() {
        mQuestionDataSource.getMoreUserQuestions(mUserId!!, object : LoadCallback<Question> {
            override fun onLoadSuccess(list: List<Question>) {
                adapter.notifyDataSetChanged()
            }

            override fun onLoadFailed(errorMsg: String) {

            }
        })
    }

    private fun initUser() {
        mUserId = intent.getStringExtra(EXTRA_USER_ID)
        if (mUserId == null) {
            mUserId = AVUser.getCurrentUser(User::class.java).objectId
        }
        if (mUserId == AVUser.getCurrentUser().objectId) {
            setTitle(R.string.my_questions)
        } else {
            setTitle(R.string.his_questions)
        }
    }

    override fun startLoadData() {
        initUser()
        mQuestionDataSource.getUserQuestions(mUserId!!, object : LoadCallback<Question> {
            override fun onLoadSuccess(list: List<Question>) {
                mSwipeRefreshLayout.isRefreshing = false
                adapter.replaceData(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        })
    }

    override fun onCreateAdapter(): BaseListAdapter<Question> {
        return UserQuestionListAdapter(this)
    }

    override fun onListItemClick(view: View, position: Int) {
        val question = adapter.getItem(position)
        val title = view.findViewById<View>(R.id.mQuestionTitle)
        val des = view.findViewById<View>(R.id.mQuestionDescription)
        transitionToQuestionDetail(this, question, title, des)
    }

}
