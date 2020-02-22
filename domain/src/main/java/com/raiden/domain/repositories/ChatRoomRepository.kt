package com.raiden.domain.repositories

import com.raiden.domain.models.User
import io.reactivex.Observable

interface ChatRoomRepository {
    fun selectForChat(user: User): Observable<Nothing>
    fun getSelectedUserForChat(): Observable<User>
}