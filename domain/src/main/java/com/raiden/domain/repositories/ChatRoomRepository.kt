package com.raiden.domain.repositories

import com.raiden.domain.models.Dialog
import com.raiden.domain.models.Message
import com.raiden.domain.models.User
import io.reactivex.Completable
import io.reactivex.Observable

interface ChatRoomRepository {
    fun selectForChat(user: User): Observable<Nothing>
    fun getSelectedUserForChat(): Observable<User>
    fun sendMessage(message: Message): Completable
    fun getAllMessages(receiverId: Int): Observable<List<Message>>
    fun subscribeOnIncomingMessages(): Observable<Message>
    fun getCurrentDialog(): Observable<Dialog>
}