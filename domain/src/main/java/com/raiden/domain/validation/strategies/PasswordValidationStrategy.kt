package com.raiden.domain.validation.strategies

class PasswordValidationStrategy(
    private val password: String
) : ValidationStrategy {
    private companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }

    override fun getField(): String {
        return password
    }

    override fun validate(): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH && password.isNotBlank()
    }
}