package com.example.permission_helper.data.source.remote.api_object

import com.example.permission_helper.data.model.User
import com.example.permission_helper.data.source.remote.api_setting.ApiLink
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable

object ApiUser {

    fun getUser(user: Int): Observable<User>? {
        return Rx2AndroidNetworking
            .get(ApiLink.getUser)
            .addPathParameter("userId", user.toString())
            .build().getObjectObservable(User::class.java)
    }

}