package com.raiden.chats.models

import com.raiden.core.mvi.CoreState
import com.raiden.domain.models.Dialog

data class State(
    val chats: List<Dialog> = emptyList(),
    val isShowLoading: Boolean = false,
    val error: Throwable? = null
): CoreState {
}