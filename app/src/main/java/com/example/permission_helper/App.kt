package com.example.permission_helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.permission_helper.data.source.local.AppDataBase
import com.example.testrecyclerviewdt.helper.SharedPreferencesHelper

@SuppressLint("Registered")
class App : Application() {
    private lateinit var mSharedHelper: SharedPreferencesHelper
    private lateinit var mDb: AppDataBase

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mSharedHelper = SharedPreferencesHelper(this)
        mDb = AppDataBase.getInstance(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    fun getSharedPre(): SharedPreferencesHelper {
        return mSharedHelper
    }

    fun getDatabase(): AppDataBase {
        return mDb
    }
}