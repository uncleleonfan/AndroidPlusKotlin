package com.leon.androidpluskotlin.utils

import android.content.Context
import com.leon.androidpluskotlin.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


val DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

private val ONE_MINUTE = 60000L
private val ONE_HOUR = 3600000L
private val ONE_DAY = 86400000L
private val ONE_WEEK = 604800000L

// 日期格式化
private var dateTimeFormat: DateFormat = SimpleDateFormat(DATETIME_DEFAULT_FORMAT)


fun getDisplayString(context: Context, date: Date): String {
    val delta = Date().time - date.time
    if (delta < ONE_MINUTE) {
        return context.getString(R.string.just)
    }
    if (delta < 45L * ONE_MINUTE) {
        val minutes = toMinutes(delta)
        return String.format(context.getString(R.string.minute_ago), if (minutes <= 0) 1 else minutes)
    }
    if (delta < 24L * ONE_HOUR) {
        val hours = toHours(delta)
        return String.format(context.getString(R.string.hour_ago), if (hours <= 0) 1 else hours)
    }
    if (delta < 48L * ONE_HOUR) {
        return context.getString(R.string.yesterday)
    }
    if (delta < 30L * ONE_DAY) {
        val days = toDays(delta)
        return String.format(context.getString(R.string.day_ago), if (days <= 0) 1 else days)
    }
    if (delta < 12L * 4L * ONE_WEEK) {
        val months = toMonths(delta)
        return String.format(context.getString(R.string.month_ago), if (months <= 0) 1 else months)
    } else {
        val years = toYears(delta)
        return String.format(context.getString(R.string.year_ago), if (years <= 0) 1 else years)
    }
}

private fun toSeconds(date: Long): Long {
    return date / 1000L
}

private fun toMinutes(date: Long): Long {
    return toSeconds(date) / 60L
}

private fun toHours(date: Long): Long {
    return toMinutes(date) / 60L
}

private fun toDays(date: Long): Long {
    return toHours(date) / 24L
}

private fun toMonths(date: Long): Long {
    return toDays(date) / 30L
}

private fun toYears(date: Long): Long {
    return toMonths(date) / 365L
}


/**
 * 日期格式化yyyy-MM-dd HH:mm:ss
 *
 * @param date
 * @return
 */
fun getDateTimeFormat(date: Date): String {
    return dateTimeFormat.format(date)
}



