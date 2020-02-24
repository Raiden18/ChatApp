package com.raiden.domain.usecases.chatroom.user.select

import com.raiden.domain.models.User
import io.reactivex.Observable

interface SelectUserForChat {
    operator fun invoke(user: User): Observable<Nothing>
}