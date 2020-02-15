package com.raiden.domain.usecases.login

import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatGateway
import io.reactivex.Single

class LogInUseCaseImpl(
    private val chatGateway: ChatGateway
) : LogInUseCase {
    override fun invoke(login: String, password: String): Single<User> {
        return chatGateway.logIn(login, password)
    }
}