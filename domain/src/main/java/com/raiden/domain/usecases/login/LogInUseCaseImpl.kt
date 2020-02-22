package com.raiden.domain.usecases.login

import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatRepository
import io.reactivex.Single

class LogInUseCaseImpl(
    private val chatGateway: ChatRepository
) : LogInUseCase {
    override fun invoke(login: String, password: String): Single<User> {
        return chatGateway.logIn(login, password)
    }
}