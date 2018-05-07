package com.leon.androidpluskotlin.di


import com.leon.androidpluskotlin.ui.fragment.ArticleFragment
import com.leon.androidpluskotlin.ui.fragment.HomeFragment
import com.leon.androidpluskotlin.ui.fragment.MeFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = arrayOf(HomeModule::class))
    internal abstract fun homeFragment(): HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = arrayOf(ArticleModule::class))
    internal abstract fun articleFragment(): ArticleFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun meFragment(): MeFragment
}
