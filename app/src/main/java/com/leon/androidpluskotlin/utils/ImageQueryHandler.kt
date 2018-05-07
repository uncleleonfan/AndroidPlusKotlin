package com.leon.androidpluskotlin.utils

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore

class ImageQueryHandler(cr: ContentResolver) : AsyncQueryHandler(cr) {

    override fun onQueryComplete(token: Int, cookie: Any, cursor: Cursor) {
        cursor.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
        saveAvatar(path)
        cursor.close()
    }
}
