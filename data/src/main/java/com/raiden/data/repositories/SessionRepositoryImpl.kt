package com.raiden.data.repositories

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import com.quickblox.users.model.QBUser
import com.raiden.data.frameworks.quickblox.adapters.QBUsersRxAdapter
import com.raiden.domain.models.User
import com.raiden.domain.repositories.SessionRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class SessionRepositoryImpl(
    private val qbUsersRxAdapter: QBUsersRxAdapter
) : SessionRepository {
    private val selectedUserForChat = BehaviorRelay.create<User>()

    override fun getUser(): Observable<User> {
        return selectedUserForChat
            .doOnNext { Log.i("1488-GET_", it.id.toString()) }
    }

    override fun saveUser(user: User): Observable<Unit> {
        selectedUserForChat.accept(user)
        return Observable.empty()
    }

    override fun logIn(email: String, password: String): Observable<User> {
        val qbUser = QBUser(email, password)
        return qbUsersRxAdapter.createSession(qbUser)
            .flatMapObservable { qbUsersRxAdapter.logIn(it).toObservable() }
            .flatMap { qbUsersRxAdapter.getUserById(it.id).toObservable() }
            .map { User(it.id, it.email, it.fullName) }
            .subscribeOn(AndroidSchedulers.mainThread())

    }
}