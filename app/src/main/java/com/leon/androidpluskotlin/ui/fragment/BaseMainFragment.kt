package com.leon.androidpluskotlin.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import com.leon.androidpluskotlin.R
import kotlinx.android.synthetic.main.fragment_base_main.*


abstract class BaseMainFragment : BaseFragment() {


    override fun getLayoutResId(): Int = R.layout.fragment_base_main

    override fun init() {
        super.init()
        mViewPager.adapter = getPagerAdapter()
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.tabMode = getTabScrollMode()
    }

    open fun getTabScrollMode(): Int = TabLayout.MODE_SCROLLABLE

    abstract fun getPagerAdapter(): PagerAdapter
}
