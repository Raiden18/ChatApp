package com.raiden.chats.models

import com.raiden.domain.models.Dialog

sealed class Change {
    object EmptyChats : Change()
    object ShowLoading : Change()
    object Idle : Change()
    data class ShowChats(val chats: List<Dialog>) : Change()
}