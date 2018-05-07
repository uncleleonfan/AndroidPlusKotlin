package com.leon.androidpluskotlin.di


import com.leon.androidpluskotlin.ui.fragment.DynamicFragment
import com.leon.androidpluskotlin.ui.fragment.HotQuestionFragment
import com.leon.androidpluskotlin.ui.fragment.QuestionFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class HomeModule {

    @ChildFragmentScope
    @ContributesAndroidInjector
    internal abstract fun questionFragment(): QuestionFragment

    @ChildFragmentScope
    @ContributesAndroidInjector
    internal abstract fun dynamicFragment(): DynamicFragment

    @ChildFragmentScope
    @ContributesAndroidInjector
    internal abstract fun hotFragment(): HotQuestionFragment
}
