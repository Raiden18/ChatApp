package com.raiden.domain.usecases.chatroom.user.get

import com.raiden.domain.models.User
import io.reactivex.Observable

interface GetSelectedUserForChat {
    operator fun invoke(): Observable<User>
}