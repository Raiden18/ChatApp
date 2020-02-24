package com.raiden.data.repositories

import com.raiden.data.frameworks.quickblox.adapters.QBRxAdapter
import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class ChatRepositoryImpl(
    private val qbUsersRxAdapter: QBRxAdapter
) : ChatRepository {

    override fun getUsers(page: Int, perPage: Int): Single<ArrayList<User>> {
        return qbUsersRxAdapter.getUsers(page, perPage)
            .toObservable()
            .switchMap { users -> Observable.fromIterable(users) }
            .map { User(it.id, it.email, it.fullName) }
            .toList()
            .map { ArrayList(it) }
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}