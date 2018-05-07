package com.leon.androidpluskotlin.presenter


import com.leon.androidpluskotlin.contract.ArticleCategoryContract
import com.leon.androidpluskotlin.data.ArticleDataSource
import com.leon.androidpluskotlin.data.LoadCallback
import com.leon.androidpluskotlin.data.model.Article

import javax.inject.Inject

class ArticleCategoryPresenter @Inject
internal constructor(private val mArticleDataSource: ArticleDataSource) : ArticleCategoryContract.Presenter {

    private var mView: ArticleCategoryContract.View? = null

    override fun takeView(view: ArticleCategoryContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }

    override fun loadArticleByTag(tag: Int) {
        mArticleDataSource.getArticlesByTag(tag, object : LoadCallback<Article> {
            override fun onLoadSuccess(list: List<Article>) {
                mView?.onLoadArticleSuccess(list)

            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadArticleFailed()

            }
        })
    }

    override fun loadMoreArticleByTag(tag: Int) {
        mArticleDataSource.getMoreArticlesByTag(tag, object : LoadCallback<Article> {
            override fun onLoadSuccess(list: List<Article>) {
                mView?.onLoadMoreArticleSuccess()
            }

            override fun onLoadFailed(errorMsg: String) {
                mView?.onLoadMoreArticleFailed()
            }
        })
    }
}
