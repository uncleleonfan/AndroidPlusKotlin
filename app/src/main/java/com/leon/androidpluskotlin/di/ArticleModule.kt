package com.leon.androidpluskotlin.di


import com.leon.androidpluskotlin.ui.fragment.ArticleCategoryFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ArticleModule {

    @ChildFragmentScope
    @ContributesAndroidInjector
    internal abstract fun articleFragment(): ArticleCategoryFragment
}
