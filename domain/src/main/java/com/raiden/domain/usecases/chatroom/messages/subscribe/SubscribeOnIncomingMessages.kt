package com.raiden.domain.usecases.chatroom.messages.subscribe

import com.raiden.domain.models.Message
import io.reactivex.Observable

interface SubscribeOnIncomingMessages {
    operator fun invoke(): Observable<Message>
}