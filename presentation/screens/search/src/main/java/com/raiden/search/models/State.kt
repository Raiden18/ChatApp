package com.raiden.search.models

import com.raiden.core.mvi.CoreState

sealed class State : CoreState {
    data class ContentState(val users: List<UserViewModel> = emptyList()) : State()
    object LoaderState : State()
    data class ErrorState(val throwable: Throwable) : State()
    object EmptyState : State()
    object Idle : State()
}