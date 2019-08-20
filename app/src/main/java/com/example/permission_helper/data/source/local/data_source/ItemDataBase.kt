package com.example.permission_helper.data.source.local.data_source

import com.example.permission_helper.data.model.User
import com.example.permission_helper.data.source.iData
import com.example.permission_helper.util.AppExecutors

class ItemDataBase private constructor(
    val appExecutors: AppExecutors,
    val itemDao: ItemDao
) : iData {

    override fun getAll(callback: iData.iLoadCallback) {

    }

    override fun getOne(id: String, callback: iData.iGetCallback) {
        appExecutors.diskIO.execute {
            val items = itemDao.getitemById(id)
            appExecutors.mainThread.execute {
                if (items != null) {
                    callback.onLoaded(items)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveUser(user: User) {
    }

    override fun deleteAll() {
    }

    override fun deleteOne(id: String) {
    }

}