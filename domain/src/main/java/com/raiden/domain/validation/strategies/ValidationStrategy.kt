package com.raiden.domain.validation.strategies

interface ValidationStrategy {
    fun getField(): String
    fun validate(): Boolean
}