package com.raiden.search.models

import com.raiden.core.mvi.CoreState
import com.raiden.domain.models.User

data class State(
    val users: List<User> = emptyList(),
    val isShowLoader: Boolean = false,
    val error: Throwable? = null,
    val idle: Boolean = false
) : CoreState