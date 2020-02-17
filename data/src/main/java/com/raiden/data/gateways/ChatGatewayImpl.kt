package com.raiden.data.gateways

import com.raiden.data.frameworks.adapters.users.QBUsersRxAdapter
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatGateway
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class ChatGatewayImpl(
    private val qbUsersRxAdapter: QBUsersRxAdapter
    ) : ChatGateway {

    override fun logIn(login: String, password: String): Single<User> {
        return qbUsersRxAdapter.logIn(login, password)
            .map { User(it.email, "") }
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun getUsers(page: Int, perPage: Int): Single<ArrayList<User>> {
        return qbUsersRxAdapter.getUsers(page, perPage)
            .toObservable()
            .switchMap { users -> Observable.fromIterable(users) }
            .map { User(it.email, "") }
            .toList()
            .map { ArrayList(it) }
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}