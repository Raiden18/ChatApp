package com.raiden.chat.model

import com.raiden.chat.model.message.MessageViewModel
import com.raiden.core.mvi.CoreState

sealed class State : CoreState {
    object Idle : State()
    data class Messages(val messages: List<MessageViewModel>) : State()
    object Loading : State()
}