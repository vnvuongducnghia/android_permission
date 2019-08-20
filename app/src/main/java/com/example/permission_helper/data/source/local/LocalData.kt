package com.example.permission_helper.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.permission_helper.data.source.local.data_source.ItemDataBase

abstract class LocalData : RoomDatabase() {

    companion object {
        private var INSTANCE: LocalData? = null
        private var lock = Any()
        fun getInstance(context: Context): LocalData {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LocalData::class.java, "Demo.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

    abstract fun taskDao(): ItemDataBase

}