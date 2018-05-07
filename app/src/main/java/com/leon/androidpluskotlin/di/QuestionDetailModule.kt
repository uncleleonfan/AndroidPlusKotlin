package com.leon.androidpluskotlin.di


import com.leon.androidpluskotlin.ui.fragment.HotAnswerFragment
import com.leon.androidpluskotlin.ui.fragment.RecentAnswerFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class QuestionDetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun recentAnswerFragment(): RecentAnswerFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun hotAnswerFragment(): HotAnswerFragment
}
