package com.raiden.chat.model

import com.raiden.core.mvi.CoreState

sealed class State : CoreState {
    object Idle : State()
}