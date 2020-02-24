package com.raiden.domain.usecases.chats.get

import com.raiden.domain.models.Chat
import io.reactivex.Single

class GetAllChatsUseCaseImpl : GetAllChatsUseCase {
    override fun invoke(): Single<List<Chat>> {
        return Single.just(listOf())
    }
}