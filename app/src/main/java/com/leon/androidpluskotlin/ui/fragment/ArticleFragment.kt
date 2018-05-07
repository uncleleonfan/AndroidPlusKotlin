package com.leon.androidpluskotlin.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter

import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.ArticlePagerAdapter
import com.leon.androidpluskotlin.di.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class ArticleFragment @Inject
constructor() : BaseMainFragment() {

    override fun getTabScrollMode(): Int {
        return TabLayout.MODE_SCROLLABLE
    }

    override fun getPagerAdapter(): PagerAdapter {
        return ArticlePagerAdapter(childFragmentManager,
                resources.getStringArray(R.array.article_category))
    }
}
