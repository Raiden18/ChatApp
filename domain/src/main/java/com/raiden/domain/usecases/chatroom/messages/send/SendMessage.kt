package com.raiden.domain.usecases.chatroom.messages.send

import io.reactivex.Observable

interface SendMessage {
    operator fun invoke(text: String): Observable<Nothing>
}