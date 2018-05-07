package com.leon.androidpluskotlin.ui.fragment


import com.leon.androidpluskotlin.adapter.AnswerListAdapter
import com.leon.androidpluskotlin.adapter.BaseLoadingListAdapter
import com.leon.androidpluskotlin.data.model.Answer

abstract class BaseAnswerFragment : BaseRefreshableListFragment<Answer>() {

    override fun onCreateAdapter(): BaseLoadingListAdapter<Answer> {
        return AnswerListAdapter(context)
    }

    override fun isEnableScrollEvent(): Boolean  = false


}
