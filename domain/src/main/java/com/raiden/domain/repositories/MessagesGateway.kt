package com.raiden.domain.repositories

import com.raiden.domain.models.Message
import io.reactivex.Observable

interface MessagesGateway {
    fun getHistoryMessages(): Observable<List<Message>>
}