package com.leon.androidpluskotlin.utils

import android.util.Patterns

fun String.isValidPassword(): Boolean {
    return this.matches(Regex("^[a-z0-9]{6,32}$"))
}

fun String.isValidUserName(): Boolean {
    return this.matches(Regex("^[\\w]{5,17}$")) || this.matches(Regex("[\\u4E00-\\u9FA5]*"))
}

fun String.matchShareUrl(): String {
    val matcher = Patterns.WEB_URL.matcher(this)
    if(matcher.find()) {
        return matcher.group()
    } else return ""
}