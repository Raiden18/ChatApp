package com.raiden.domain.usecases.chat.get

import com.raiden.domain.models.Chat
import io.reactivex.Observable
import io.reactivex.Single

class GetAllChatsUseCaseImpl : GetAllChatsUseCase {
    override fun invoke(): Single<List<Chat>> {
        return Single.just(listOf())
    }
}