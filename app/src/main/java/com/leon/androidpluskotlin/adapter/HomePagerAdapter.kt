package com.leon.androidpluskotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.leon.androidpluskotlin.ui.fragment.DynamicFragment
import com.leon.androidpluskotlin.ui.fragment.HotQuestionFragment
import com.leon.androidpluskotlin.ui.fragment.QuestionFragment


class HomePagerAdapter(fm: FragmentManager, private val mTitles: Array<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? =
        when (position) {
            0 ->  QuestionFragment()
            1 ->  HotQuestionFragment()
            2 ->  DynamicFragment()
            else -> null
        }



    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}
