package com.example.permission_helper.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import com.example.permission_helper.App
import com.example.permission_helper.BuildConfig


object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        // Checking internet connectivity
        val connectivityMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork: NetworkInfo? = null
        activeNetwork = connectivityMgr.activeNetworkInfo
        return activeNetwork != null
    }

    fun getDeviceInfo(): String { // android,OS8,ver4.3.2
        return "android,OS" + Build.VERSION.RELEASE + ",ver" + BuildConfig.VERSION_NAME
    }

    @SuppressLint("HardwareIds")
    fun getDeviceCode(): String { // androidId
        return Settings.Secure.getString(App.instance.contentResolver, Settings.Secure.ANDROID_ID)
    }


}