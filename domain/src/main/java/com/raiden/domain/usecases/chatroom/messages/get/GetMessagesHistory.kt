package com.raiden.domain.usecases.chatroom.messages.get

import com.raiden.domain.models.Message
import io.reactivex.Observable

interface GetMessagesHistory {
    operator fun invoke(): Observable<List<Message>>
}