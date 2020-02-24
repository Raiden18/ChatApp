package com.raiden.chats.models

import com.raiden.core.mvi.CoreState
import com.raiden.domain.models.Chat

data class State(
    val chats: List<Chat> = emptyList(),
    val isShowLoading: Boolean = false,
    val error: Throwable? = null
): CoreState {
}