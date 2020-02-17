package com.raiden.data.frameworks.adapters.users

import com.quickblox.users.model.QBUser
import io.reactivex.Single

interface QBUsersRxAdapter {
    fun logIn(login: String, password: String): Single<QBUser>

    fun getUsers(page: Int, perPage: Int): Single<ArrayList<QBUser>>
}