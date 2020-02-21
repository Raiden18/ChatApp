package com.raiden.domain.usecases.chat.history

import com.raiden.domain.models.Message
import io.reactivex.Observable

interface GetMessagesHistoryUseCase {
    operator fun invoke(): Observable<List<Message>>
}