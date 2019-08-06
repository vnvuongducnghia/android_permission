package com.example.permission_helper.util

import android.util.Log
import com.example.permission_helper.BuildConfig

object AppLog {
    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg)
        }
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg, tr)
        }
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, tr)
        }
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg, tr)
        }
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg, tr)
        }
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg, tr)
        }
    }

    fun w(tag: String, tr: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, tr)
        }
    }
}