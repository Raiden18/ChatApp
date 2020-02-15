package com.raiden.domain.validation

import com.raiden.domain.validation.strategies.ValidationStrategy

class Validator {
    private lateinit var validationStrategy: ValidationStrategy

    fun setStrategy(validationStrategy: ValidationStrategy) {
        this.validationStrategy = validationStrategy
    }

    fun validate() = validationStrategy.validate()
}