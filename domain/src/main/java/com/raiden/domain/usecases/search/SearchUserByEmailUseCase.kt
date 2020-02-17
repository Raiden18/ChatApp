package com.raiden.domain.usecases.search

import com.raiden.domain.models.User
import io.reactivex.Observable
import io.reactivex.Single

interface SearchUserByEmailUseCase {
    operator fun invoke(email: String): Observable<ArrayList<User>>
}