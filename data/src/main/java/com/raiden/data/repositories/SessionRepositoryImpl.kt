package com.raiden.data.repositories

import com.jakewharton.rxrelay2.BehaviorRelay
import com.raiden.data.frameworks.quickblox.adapters.users.QBUsersRxAdapter
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
    }

    override fun saveUser(user: User): Observable<Unit> {
        selectedUserForChat.accept(user)
        return Observable.empty()
    }

    override fun logIn(email: String, password: String): Observable<User> {
        return qbUsersRxAdapter.logIn(email, password)
            .map { User(it.email, it.fullName) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .toObservable()
    }
}