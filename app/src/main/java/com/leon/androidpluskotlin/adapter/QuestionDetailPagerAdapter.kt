package com.leon.androidpluskotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.leon.androidpluskotlin.ui.fragment.HotAnswerFragment
import com.leon.androidpluskotlin.ui.fragment.RecentAnswerFragment


class QuestionDetailPagerAdapter(fm: FragmentManager, private val mTitles: Array<String>, private val mQuestionId: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            RecentAnswerFragment.newInstance(mQuestionId)
        } else {
            HotAnswerFragment.newInstance(mQuestionId)
        }
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}
