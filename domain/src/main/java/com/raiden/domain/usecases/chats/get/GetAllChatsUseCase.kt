package com.raiden.domain.usecases.chats.get

import com.raiden.domain.models.Chat
import io.reactivex.Single

interface GetAllChatsUseCase {
    operator fun invoke(): Single<List<Chat>>
}