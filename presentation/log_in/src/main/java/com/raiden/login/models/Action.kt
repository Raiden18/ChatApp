package com.raiden.login.models

import com.raiden.core.mvi.CoreAction

sealed class Action : CoreAction {
    object ShowEmpty : Action()
}