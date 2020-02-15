package com.raiden.threestatestextinputlayout

sealed class FieldState {
    object InvalidState : FieldState() {
        override fun toString(): String {
            return "InvalidState"
        }
    }

    object ValidState : FieldState() {
        override fun toString(): String {
            return "ValidState"
        }
    }

    object EmptyState : FieldState() {
        override fun toString(): String {
            return "EmptyState"
        }
    }
}