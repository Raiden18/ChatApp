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

    private var currentPage = 0

    override fun invoke(email: String): Observable<ArrayList<User>> {
        return usersGateway.getUsers(currentPage, USERS_PER_PAGE)
            .doOnSuccess { currentPage++ }
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