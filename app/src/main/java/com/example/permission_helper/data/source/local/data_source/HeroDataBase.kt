package com.example.permission_helper.data.source.local.data_source

import com.example.permission_helper.data.model.User
import com.example.permission_helper.data.source.iData

class HeroDataBase : iData {

    override fun getAll(callback: iData.iLoadCallback) {
    }

    override fun getOne(id: String, callback: iData.iGetCallback) {
    }

    override fun saveUser(user: User) {
    }

    override fun deleteAll() {
    }

    override fun deleteOne(id: String) {
    }
}