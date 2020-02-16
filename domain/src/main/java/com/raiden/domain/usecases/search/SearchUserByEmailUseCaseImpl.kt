package com.raiden.domain.usecases.search

import com.raiden.domain.models.User
import io.reactivex.Observable
import io.reactivex.Single

class SearchUserByEmailUseCaseImpl : SearchUserByEmailUseCase {
    override fun invoke(email: String): Observable<List<User>> {
        return Observable.empty()
    }
}