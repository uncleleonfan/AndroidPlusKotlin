package com.leon.androidpluskotlin.ui.activity

import android.content.Intent
import android.view.View

import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.BaseListAdapter
import com.leon.androidpluskotlin.adapter.UserArticleListAdapter
import com.leon.androidpluskotlin.app.EXTRA_ARTICLE
import com.leon.androidpluskotlin.app.EXTRA_USER_ID
import com.leon.androidpluskotlin.data.ArticleDataSource
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.data.model.User
import kotlinx.android.synthetic.main.activity_base_list_view.*

import javax.inject.Inject


class UserShareArticleActivity : BaseListViewActivity<Article>() {

    @Inject
    lateinit var mArticleDataSource: ArticleDataSource

    private var mUserId: String? = null


    override fun startLoadMoreData() {
        mArticleDataSource.getMoreUserSharedArticles(mUserId!!, object : LoadCallback<Article> {
            override fun onLoadSuccess(list: List<Article>) {
                adapter.notifyDataSetChanged()
            }

            override fun onLoadFailed(errorMsg: String) {

            }
        })
    }

    override fun startLoadData() {
        initUser()
        mArticleDataSource.getUserSharedArticles(mUserId!!, object : LoadCallback<Article> {
            override fun onLoadSuccess(list: List<Article>) {
                mSwipeRefreshLayout.isRefreshing = false
                adapter.replaceData(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun initUser() {
        mUserId = intent.getStringExtra(EXTRA_USER_ID)
        if (mUserId == null) {
            mUserId = AVUser.getCurrentUser(User::class.java).objectId
        }
        if (mUserId == AVUser.getCurrentUser().objectId) {
            setTitle(R.string.my_share)
        } else {
            setTitle(R.string.his_share)
        }
    }

    override fun onCreateAdapter(): BaseListAdapter<Article> {
        return UserArticleListAdapter(this)
    }

    override fun startRefresh() {
        mArticleDataSource.refreshUserShareArticles(mUserId!!, object : LoadCallback<Article> {
            override fun onLoadSuccess(list: List<Article>) {
                mSwipeRefreshLayout.isRefreshing = false
                adapter.replaceData(list)
            }

            override fun onLoadFailed(errorMsg: String) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        })
    }

    override fun onListItemClick(view: View, position: Int) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        val article = adapter.getItem(position)
        intent.putExtra(EXTRA_ARTICLE, article.toString())
        startActivity(intent)
    }
}
