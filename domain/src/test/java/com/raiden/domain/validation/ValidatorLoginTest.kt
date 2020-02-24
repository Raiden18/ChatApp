package com.raiden.domain.validation

import com.raiden.domain.validation.strategies.PasswordValidationStrategy
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidatorLoginTest {
    private lateinit var validator: Validator


    @Before
    fun setUp() {
        validator = Validator()
    }

    @Test
    fun `Should true if password is 8 characters long`() {
        assertTruePassword("12345678")
    }

    @Test
    fun `Should return true if password is more that 8 characters long`() {
        assertTruePassword("123456789")
    }

    @Test
    fun `Should return false if password is less than 8 characters long`() {
        assertFalsePassword("1234567")
    }

    @Test
    fun `Should return false if password is 8 characters long of spaces`() {
        assertFalsePassword("        ")
    }

    private fun assertTruePassword(password: String) {
        //given
        val passwordStrategy = PasswordValidationStrategy(password)

        //When
        validator.setStrategy(passwordStrategy)
        val factResult = passwordStrategy.validate()

        //Then
        assertTrue(factResult)
    }

    private fun assertFalsePassword(password: String) {
        //given
        val passwordStrategy = PasswordValidationStrategy(password)

        //When
        validator.setStrategy(passwordStrategy)
        val factResult = passwordStrategy.validate()

        //Then
        assertFalse(factResult)
    }
}