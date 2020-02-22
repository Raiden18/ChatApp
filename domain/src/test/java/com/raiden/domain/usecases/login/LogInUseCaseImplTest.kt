package com.raiden.domain.usecases.login

import com.raiden.domain.models.User
import com.raiden.domain.repositories.SessionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class LogInUseCaseImplTest {

    private lateinit var sessionRepository: SessionRepository
    private lateinit var logInUseCase: LogInUseCase

    @Before
    fun setUp() {
        sessionRepository = spyk()
        logInUseCase = LogInUseCaseImpl(sessionRepository)
    }

    @Test
    fun `Should log in user and then save user`() {
        //Given
        val user: User = mockk(relaxed = true)
        val email = "asdasd"
        val password = "312312313"
        every {
            sessionRepository.logIn(any(), any())
        } returns Observable.just(user)

        //When
        logInUseCase.invoke(email, password).blockingFirst()

        //Then
        verify {
            sessionRepository.logIn(email, password)
            sessionRepository.saveUser(user)
        }

    }
}