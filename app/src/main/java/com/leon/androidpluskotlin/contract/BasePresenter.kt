package com.leon.androidpluskotlin.contract

interface BasePresenter<T> {
    fun takeView(view: T)
    fun dropView()
}
