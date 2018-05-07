package com.leon.androidpluskotlin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SaveCallback
import com.leon.androidpluskotlin.data.model.User
import java.io.FileNotFoundException


@SuppressLint("NewApi")
fun saveAvatar(context: Context, uri: Uri) {
    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        val docId = DocumentsContract.getDocumentId(uri)
        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = "_id=?"
        val selectionArgs = arrayOf(split[1])
        startQuery(context, contentUri, selection, selectionArgs)
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {// MediaStore
        startQuery(context, uri, null, null)
    }
}

fun startQuery(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?) {
    val imageQueryHandler = ImageQueryHandler(context.contentResolver)
    val projections = arrayOf(MediaStore.MediaColumns.DATA)
    imageQueryHandler.startQuery(0, null, uri, projections, selection, selectionArgs, null)
}

fun saveAvatar(path: String) {
    val fileName = System.currentTimeMillis().toString() + ".png"
    saveAvatar(fileName, path)
}

fun saveAvatar(fileName: String, filePath: String) {
    try {
        val file = AVFile.withAbsoluteLocalPath(fileName, filePath)
        file.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    val user = AVUser.getCurrentUser(User::class.java)
                    user.avatar = file.url
                    user.saveInBackground()
                } else {
                }
            }
        })
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }

}

