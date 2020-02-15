package com.raiden.chats.models

import com.raiden.domain.models.Chat

sealed class Change {
    object EmptyChats : Change()
    object ShowLoading : Change()
    data class ShowChats(val chats: List<Chat>) : Change()
}