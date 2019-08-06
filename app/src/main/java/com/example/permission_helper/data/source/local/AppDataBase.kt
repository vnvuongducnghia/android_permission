package com.example.permission_helper.data.source.local

import android.content.Context

class AppDataBase {

    companion object {
        private var INSTANCE: AppDataBase? = null
        private var lock = Any()
        fun getInstance(context: Context): AppDataBase {
            synchronized(lock) {
                if (INSTANCE == null) {

                }
                return INSTANCE!!
            }
        }
    }
}