package com.example.permission_helper.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object CommonUtils {

    const val TRANSITION_DELAY = 300L

    @SuppressLint("all")
    fun getDeviceId(context: Context): String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

}