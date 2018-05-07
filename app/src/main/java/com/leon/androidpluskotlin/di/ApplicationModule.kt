package com.leon.androidpluskotlin.di

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module

@Module
internal abstract class ApplicationModule {

    @Binds
    internal abstract fun bindContext(application: Application): Context
}
