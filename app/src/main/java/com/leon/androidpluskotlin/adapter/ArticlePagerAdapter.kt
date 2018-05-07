package com.leon.androidpluskotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.ui.fragment.ArticleCategoryFragment


class ArticlePagerAdapter(fm: FragmentManager, private val mTitles: Array<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            Article.TAG_KOTLIN -> return ArticleCategoryFragment.newInstance(Article.TAG_KOTLIN)
            Article.TAG_SDK -> return ArticleCategoryFragment.newInstance(Article.TAG_SDK)
            Article.TAG_CUSTOM_VIEW -> return ArticleCategoryFragment.newInstance(Article.TAG_CUSTOM_VIEW)
            Article.TAG_THIRD_PARTY -> return ArticleCategoryFragment.newInstance(Article.TAG_THIRD_PARTY)
            Article.TAG_HOT -> return ArticleCategoryFragment.newInstance(Article.TAG_HOT)
            Article.TAG_PROJECT -> return ArticleCategoryFragment.newInstance(Article.TAG_PROJECT)
            Article.TAG_THINKING -> return ArticleCategoryFragment.newInstance(Article.TAG_THINKING)
            Article.TAG_INTERVIEW -> return ArticleCategoryFragment.newInstance(Article.TAG_INTERVIEW)
        }
        return null
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}
