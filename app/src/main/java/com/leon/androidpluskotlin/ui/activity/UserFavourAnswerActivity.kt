package com.leon.androidpluskotlin.ui.activity

import android.view.View
import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.BaseListAdapter
import com.leon.androidpluskotlin.adapter.UserAnswerAdapter
import com.leon.androidpluskotlin.app.EXTRA_USER_ID
import com.leon.androidpluskotlin.data.AnswerDataSource
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.utils.transitionToAnswerDetail
import kotlinx.android.synthetic.main.activity_base_list_view.*
import javax.inject.Inject

class UserFavourAnswerActivity : BaseListViewActivity<Answer>() {

    @Inject
    lateinit var mAnswerDataSource: AnswerDataSource

    var mUserId: String? = null

    private fun initUser() {
        mUserId = intent.getStringExtra(EXTRA_USER_ID)
        if (mUserId == null) {
            mUserId = AVUser.getCurrentUser(User::class.java).objectId
        }
        if (mUserId == AVUser.getCurrentUser().objectId) {
            setTitle(R.string.my_favour_answers)
        } else {
            setTitle(R.string.his_favour_questions)
        }
    }

    override fun startRefresh() {
        mAnswerDataSource.refreshUserFavouredAnswers(mUserId!!, object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                adapter.replaceData(list)
                mSwipeRefreshLayout.isRefreshing = false
            }

            override fun onLoadFailed(errorMsg: String) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        })
    }

    override fun startLoadMoreData() {
        mAnswerDataSource.getMoreUserFavouredAnswers(mUserId!!, object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                adapter.notifyDataSetChanged()
            }

            override fun onLoadFailed(errorMsg: String) {

            }
        })
    }

    override fun startLoadData() {
        initUser()
        mAnswerDataSource.getUserFavouredAnswers(mUserId!!, object : LoadCallback<Answer> {
            override fun onLoadSuccess(list: List<Answer>) {
                mSwipeRefreshLayout.isRefreshing = false
                adapter.replaceData(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        })
    }

    override fun onCreateAdapter(): BaseListAdapter<Answer> {
        return UserAnswerAdapter(this)
    }

    override fun onListItemClick(view: View, position: Int) {
        val answer = adapter.getItem(position)
        val titleView = view.findViewById<View>(R.id.mQuestionTitle)
        val answerView = view.findViewById<View>(R.id.mAnswer)
        transitionToAnswerDetail(this, answer, titleView, answerView)
    }
}
