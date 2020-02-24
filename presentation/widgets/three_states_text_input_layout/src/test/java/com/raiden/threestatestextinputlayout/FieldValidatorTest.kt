package com.raiden.threestatestextinputlayout

import com.raiden.domain.validation.strategies.ValidationStrategy
import io.mockk.every
import io.mockk.spyk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FieldValidatorTest {
    private lateinit var fieldValidator: FieldValidator
    private lateinit var validationStrategy: ValidationStrategy
    @Before
    fun setUp() {
        fieldValidator = FieldValidator()
        validationStrategy = spyk()
    }

    @Test
    fun `Should return Empty if field is empty`() {
        //Given
        every {
            validationStrategy.getField()
        } returns ""
        every {
            validationStrategy.validate()
        } returns false
        fieldValidator.setStrategy(validationStrategy)

        //When
        val resultState = fieldValidator.validate()

        //Then
        assertEquals(FieldState.EmptyState, resultState)
    }

    @Test
    fun `Should return valid state if field is valid`() {
        //Given
        every {
            validationStrategy.getField()
        } returns "123123123"
        every {
            validationStrategy.validate()
        } returns true
        fieldValidator.setStrategy(validationStrategy)

        //When
        val resultState = fieldValidator.validate()

        //Then
        assertEquals(FieldState.ValidState, resultState)
    }

    @Test
    fun `Should return invalid state if field is invalid`() {
        //Given
        every {
            validationStrategy.getField()
        } returns "123123123"
        every {
            validationStrategy.validate()
        } returns false
        fieldValidator.setStrategy(validationStrategy)

        //When
        val resultState = fieldValidator.validate()

        //Then
        assertEquals(FieldState.InvalidState, resultState)
    }
}