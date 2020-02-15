package com.raiden.threestatestextinputlayout

import com.raiden.domain.validation.strategies.ValidationStrategy

class FieldValidator {
    private lateinit var validationStrategy: ValidationStrategy

    fun setStrategy(validationStrategy: ValidationStrategy) {
        this.validationStrategy = validationStrategy
    }

    fun validate(): FieldState {
        val isValid = validationStrategy.validate()
        return when {
            validationStrategy.getField().isEmpty() -> FieldState.EmptyState
            isValid -> FieldState.ValidState
            else -> FieldState.InvalidState
        }
    }
}