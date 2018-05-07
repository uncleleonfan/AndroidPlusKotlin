package com.leon.androidpluskotlin.ui.fragment

import android.support.v4.view.PagerAdapter
import android.support.v7.widget.Toolbar
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.HomePagerAdapter
import com.leon.androidpluskotlin.di.ActivityScoped
import com.leon.androidpluskotlin.ui.activity.AddQuestionActivity
import kotlinx.android.synthetic.main.fragment_base_main.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

@ActivityScoped
class HomeFragment @Inject
constructor() : BaseMainFragment() {

    private val mOnMenuItemClickListener = Toolbar.OnMenuItemClickListener {
        context.startActivity<AddQuestionActivity>()
        true
    }


    override fun init() {
        super.init()
        mToolBar.inflateMenu(R.menu.home_menu)
        mToolBar.setOnMenuItemClickListener(mOnMenuItemClickListener)
    }

    override fun getPagerAdapter(): PagerAdapter {
        return HomePagerAdapter(childFragmentManager,
                resources.getStringArray(R.array.home_category))
    }
}
