package com.leon.androidpluskotlin.di


import com.leon.androidpluskotlin.ui.activity.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun registerActivity(): RegisterActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    internal abstract fun mainActivity(): MainActivity


    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun profileActivity(): ProfileActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(QuestionDetailModule::class))
    internal abstract fun questionDetailActivity(): QuestionDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun answerDetailActivity(): AnswerDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun addAnswerActivity(): AddAnswerActivity


    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun articleDetailActivity(): ArticleDetailActivity


    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun addQuestionActivity(): AddQuestionActivity



    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun userFavourAnswerActivity(): UserFavourAnswerActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun userFavourArticleActivity(): UserFavourArticleActivity


    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun userFavourQuestionActivity(): UserFavourQuestionActivity


    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun userQuestionsActivity(): UserQuestionsActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun userShareArticleActivity(): UserShareArticleActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun userAnswerActivity(): UserAnswerActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun settingsActivity(): SettingsActivity


    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun aboutActivity(): AboutActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun commentActivity(): CommentActivity


    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun shareArticleActivity(): ShareArticleActivity




}
