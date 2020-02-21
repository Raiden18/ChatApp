package com.raiden.chat.model

import com.raiden.core.mvi.CoreState
import com.raiden.domain.models.Message

sealed class State : CoreState {
    object Idle : State()
    data class Messages(val messages: List<Message>) : State()
    object Loading: State()
}