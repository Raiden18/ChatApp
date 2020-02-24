package com.raiden.domain.repositories

import com.raiden.domain.models.User
import io.reactivex.Observable

interface SessionRepository {
    fun getUser(): Observable<User>
    fun saveUser(user: User): Observable<Unit>
    fun logIn(email: String, password: String): Observable<User>
}