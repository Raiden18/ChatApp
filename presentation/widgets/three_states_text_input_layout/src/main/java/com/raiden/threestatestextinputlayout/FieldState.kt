package com.raiden.threestatestextinputlayout

sealed class FieldState {
    object InvalidState : FieldState()
    object ValidState : FieldState()
    object EmptyState : FieldState()
}