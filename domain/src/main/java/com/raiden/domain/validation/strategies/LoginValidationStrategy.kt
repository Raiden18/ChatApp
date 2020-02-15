package com.raiden.domain.validation.strategies

class LoginValidationStrategy(
    private val login: String
) : ValidationStrategy {
    private companion object {
        const val MIN_LOGIN_LENGTH = 6
    }

    override fun getField(): String {
        return login

    }

    override fun validate(): Boolean {
        return login.length >= MIN_LOGIN_LENGTH && login.isNotBlank()
    }
}