package com.raiden.domain.usecases.login

import com.raiden.domain.models.User
import io.reactivex.Single

interface LogInUseCase {
    operator fun invoke(login: String, password: String): Single<User>
}