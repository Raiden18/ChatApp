package com.raiden.data.repositories

import com.raiden.data.frameworks.quickblox.adapters.users.QBUsersRxAdapter
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class ChatRepositoryImpl(
    private val qbUsersRxAdapter: QBUsersRxAdapter
) : ChatRepository {

    override fun logIn(login: String, password: String): Single<User> {
        return qbUsersRxAdapter.logIn(login, password)
            .map { User(it.email, it.fullName) }
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun getUsers(page: Int, perPage: Int): Single<ArrayList<User>> {
        return qbUsersRxAdapter.getUsers(page, perPage)
            .toObservable()
            .switchMap { users -> Observable.fromIterable(users) }
            .map { User(it.email, it.fullName) }
            .toList()
            .map { ArrayList(it) }
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}