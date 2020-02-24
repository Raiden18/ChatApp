package com.raiden.domain.usecases.chats.get

import com.raiden.domain.models.Dialog
import io.reactivex.Single

class GetAllChatsUseCaseImpl : GetAllChatsUseCase {
    override fun invoke(): Single<List<Dialog>> {
        return Single.just(listOf())
    }
}