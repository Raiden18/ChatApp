package com.raiden.search.models

import com.raiden.core.mvi.CoreAction

sealed class Action : CoreAction {
    object Idle : Action()
    object GoBack : Action()
    data class Search(val email: String) : Action()
}