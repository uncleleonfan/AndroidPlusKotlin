package com.leon.androidpluskotlin.data

interface LoadCallback<T> {
    fun onLoadSuccess(list: List<T>)
    fun onLoadFailed(errorMsg: String)
}
