package com.raiden.data.repositories

import com.jakewharton.rxrelay2.BehaviorRelay
import com.raiden.data.frameworks.quickblox.adapters.QBRxAdapter
import com.raiden.domain.models.User
import com.raiden.domain.repositories.SessionRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class SessionRepositoryImpl(
    private val qbUsersRxAdapter: QBRxAdapter
) : SessionRepository {
    private val selectedUserForChat = BehaviorRelay.create<User>()

    override fun getUser(): Observable<User> {
        return selectedUserForChat
    }

    override fun saveUser(user: User): Observable<Unit> {
        selectedUserForChat.accept(user)
        return Observable.empty()
    }

    override fun logIn(email: String, password: String): Observable<User> {
        return qbUsersRxAdapter.createSession(email, password)
            .flatMap { qbUsersRxAdapter.logIn(it) }
            .map { User(it.id, it.email, it.fullName) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .toObservable()

    }
}