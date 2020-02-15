package com.raiden.domain.validation

import com.raiden.domain.validation.strategies.LoginValidationStrategy
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidatorPasswordTest {
    private lateinit var validator: Validator


    @Before
    fun setUp() {
        validator = Validator()
    }

    @Test
    fun `Should return true if login equals 6 symbols long`() {
        assertTrueLogin("123456")
    }

    @Test
    fun `Should return true if login is more than 6 symbols long`() {
        assertTrueLogin("1234567")

    }

    @Test
    fun `Should return false if login is lessThan 6 symbols long`() {
        assertFalseLogIn("123")
    }

    @Test
    fun `Should return false if login is empty`(){
        assertFalseLogIn("")
    }

    @Test
    fun `Should return false if login is 7 sympols space long`(){
        assertFalseLogIn("       ")

    }

    private fun assertTrueLogin(login: String) {
        //given
        val logInStrategy = LoginValidationStrategy(login)

        //When
        validator.setStrategy(logInStrategy)
        val factResult = validator.validate()

        //Then
        assertTrue(factResult)
    }

    private fun assertFalseLogIn(login: String) {
        //given
        val logInStrategy = LoginValidationStrategy(login)

        //When
        validator.setStrategy(logInStrategy)
        val factResult = validator.validate()

        //Then
        assertFalse(factResult)
    }
}