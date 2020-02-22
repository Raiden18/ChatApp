package com.raiden.domain.repositories

import com.raiden.domain.models.User
import io.reactivex.Observable
import io.reactivex.Single

interface ChatRepository {
    fun logIn(login: String, password: String): Single<User>
    fun getUsers(page: Int, perPage: Int): Single<ArrayList<User>>
}