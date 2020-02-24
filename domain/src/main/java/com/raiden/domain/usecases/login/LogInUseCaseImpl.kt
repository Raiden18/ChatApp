package com.raiden.domain.usecases.login

import com.raiden.domain.models.User
import com.raiden.domain.repositories.SessionRepository
import io.reactivex.Observable

class LogInUseCaseImpl(
    private val sessionRepository: SessionRepository
) : LogInUseCase {
    override fun invoke(login: String, password: String): Observable<User> {
        return sessionRepository.logIn(login, password)
            .doOnNext { sessionRepository.saveUser(it) }
    }
}