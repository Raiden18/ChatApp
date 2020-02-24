package com.raiden.domain.usecases.search

import com.raiden.domain.models.User
import io.reactivex.Observable

interface SearchUserByEmailUseCase {
    operator fun invoke(email: String, page: Int): Observable<ArrayList<User>>
}