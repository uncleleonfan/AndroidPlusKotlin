package com.leon.androidpluskotlin.contract


import com.leon.androidpluskotlin.data.model.Article

interface ArticleCategoryContract {

    interface View : BaseView {
        fun onLoadArticleSuccess(articleList: List<Article>)
        fun onLoadArticleFailed()
        fun onLoadMoreArticleSuccess()
        fun onLoadMoreArticleFailed()
    }

    interface Presenter : BasePresenter<View> {
        fun loadArticleByTag(tag: Int)
        fun loadMoreArticleByTag(tag: Int)
    }

}
