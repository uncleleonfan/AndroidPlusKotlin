package com.leon.androidpluskotlin.data

interface SaveCallback {
    fun onSaveSuccess()
    fun onSaveFailed(errorMsg: String)
}
