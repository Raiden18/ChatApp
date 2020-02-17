package com.raiden.search.models

import com.raiden.core.mvi.CoreState

data class State(
    val users: List<UserViewModel> = emptyList(),
    val isShowLoader: Boolean = false,
    val error: Throwable? = null,
    val idle: Boolean = false
) : CoreState