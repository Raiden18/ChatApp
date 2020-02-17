package com.raiden.domain.usecases.search

import com.raiden.domain.models.User
import com.raiden.domain.repositories.ChatGateway
import io.reactivex.Observable

class SearchUserByEmailUseCaseImpl(
    private val usersGateway: ChatGateway
) : SearchUserByEmailUseCase {

    companion object {
        const val USERS_PER_PAGE = 50
    }

    override fun invoke(email: String, page: Int): Observable<ArrayList<User>> {
        return usersGateway.getUsers(page, USERS_PER_PAGE)
            .toObservable()
            .flatMap { Observable.fromIterable(it) }
            .filter {
                val preparedFilterableEmail = it.email.toLowerCase().trim()
                val preparedEmail = email.toLowerCase().trim()
                preparedFilterableEmail.contains(preparedEmail)
            }
            .toList()
            .map { ArrayList(it) }
            .toObservable()
    }
}