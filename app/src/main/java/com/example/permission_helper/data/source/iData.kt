package com.example.permission_helper.data.source

import com.example.permission_helper.data.model.User

interface iData {

    interface iLoadCallback {

        fun onLoaded(any: Any)

        fun onDataNotAvailable()
    }

    interface iGetCallback {

        fun onLoaded(any: Any)

        fun onDataNotAvailable()
    }

    fun getAll(callback: iLoadCallback)

    fun getOne(id: String, callback: iGetCallback)

    fun saveUser(user: User)

    fun deleteAll()

    fun deleteOne(id: String)


}