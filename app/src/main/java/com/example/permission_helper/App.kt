package com.example.permission_helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.example.permission_helper.data.source.local.LocalData
import com.example.permission_helper.util.CommonUtils
import com.example.testrecyclerviewdt.helper.SharedPreferencesHelper

@SuppressLint("Registered")
class App : Application() {
    private lateinit var mSharedHelper: SharedPreferencesHelper
    private lateinit var mDb: LocalData

    companion object {
        lateinit var instance: App
        private var androidId:String =""
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mSharedHelper = SharedPreferencesHelper(this)
        mDb = LocalData.getInstance(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /*SharedPreference*/
    fun getSharedPre(): SharedPreferencesHelper {
        return mSharedHelper
    }

    /*Database*/
    fun getDatabase(): LocalData {
        return mDb
    }

    /*AndroidID*/
    private fun getAndroidID() {
        try {
            androidId = CommonUtils.getDeviceId(App.instance)
        } catch (e: Exception) {
            var androidIdNotFound = mSharedHelper.getString(Constant.ANDROID_ID_NOT_FOUND, "")
            if (androidIdNotFound.isNullOrEmpty()) {
                androidIdNotFound = System.currentTimeMillis().toString() + "L"
                mSharedHelper.getString(Constant.ANDROID_ID_NOT_FOUND, androidIdNotFound)
            }
            androidId = androidIdNotFound + ""
        }
    }




}