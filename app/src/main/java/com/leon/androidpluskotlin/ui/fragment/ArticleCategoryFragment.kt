package com.leon.androidpluskotlin.ui.fragment

import android.os.Bundle
import android.view.View
import com.leon.androidpluskotlin.adapter.ArticleListAdapter

import com.leon.androidpluskotlin.adapter.BaseLoadingListAdapter
import com.leon.androidpluskotlin.app.ARGUMENT_TYPE
import com.leon.androidpluskotlin.contract.ArticleCategoryContract
import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.presenter.ArticleCategoryPresenter
import kotlinx.android.synthetic.main.fragment_base_refreshable_list.*

import javax.inject.Inject

class ArticleCategoryFragment : BaseRefreshableListFragment<Article>(), ArticleCategoryContract.View {

    @Inject
    lateinit var mArticleCategoryPresenter: ArticleCategoryPresenter

    private var mTag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTag = arguments.getInt(ARGUMENT_TYPE, -1)
    }

    override fun onCreateAdapter(): BaseLoadingListAdapter<Article> {
        return ArticleListAdapter(context)
    }

    override fun init() {
        super.init()
        mArticleCategoryPresenter.takeView(this)
        mArticleCategoryPresenter.loadArticleByTag(mTag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mArticleCategoryPresenter.dropView()
    }

    override fun startRefresh() {
        mArticleCategoryPresenter.loadArticleByTag(mTag)
    }

    override fun startLoadMoreData() {
        mArticleCategoryPresenter.loadMoreArticleByTag(mTag)
    }


    override fun onLoadArticleSuccess(articleList: List<Article>) {
        mSwipeRefreshLayout.isRefreshing = false
        adapter.replaceData(articleList)
    }

    override fun onLoadArticleFailed() {
        mSwipeRefreshLayout.isRefreshing = false
        mError.visibility = View.VISIBLE
    }

    override fun onLoadMoreArticleSuccess() {
        adapter.notifyDataSetChanged()
    }

    override fun onLoadMoreArticleFailed() {

    }

    companion object {

        fun newInstance(tag: Int): ArticleCategoryFragment {
            val articleCategoryFragment = ArticleCategoryFragment()
            val bundle = Bundle()
            bundle.putInt(ARGUMENT_TYPE, tag)
            articleCategoryFragment.arguments = bundle
            return articleCategoryFragment
        }
    }
}
