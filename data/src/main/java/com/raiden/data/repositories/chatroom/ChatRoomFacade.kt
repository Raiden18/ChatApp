package com.raiden.data.repositories.chatroom

import com.raiden.domain.models.Dialog
import com.raiden.domain.models.Message
import io.reactivex.Completable
import io.reactivex.Observable

interface ChatRoomFacade {
    fun sendMessage(message: Message): Completable

    fun getAllMessages(receiverId: Int): Observable<List<Message>>

    fun subscribeOnIncomingMessages(): Observable<Message>

    fun getCurrentDialog(): Observable<Dialog>
}