package com.leon.androidpluskotlin.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import java.util.ArrayList

fun Activity.checkPermissions(): Boolean {
    val permissions = ArrayList<String>()
    val phoneStatePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
    if (phoneStatePermission == PackageManager.PERMISSION_DENIED) {
        permissions.add(Manifest.permission.READ_PHONE_STATE)
    }
    val storagePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (storagePermission == PackageManager.PERMISSION_DENIED) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    val cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
    if (cameraPermission == PackageManager.PERMISSION_DENIED) {
        permissions.add(Manifest.permission.CAMERA)
    }
    if (permissions.size > 0) {
        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 0)
        return false
    }
    return true
}
