package com.raiden.search.intent

import androidx.lifecycle.Observer
import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.core.utils.rx.schedule.SchedulerProvider
import com.raiden.domain.models.User
import com.raiden.domain.usecases.search.SearchUserByEmailUseCase
import com.raiden.search.models.Action
import com.raiden.search.models.State
import com.raiden.search.models.UserViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class SearchMviIntentTest : BaseMviIntentTest() {

    private lateinit var searchEventListener: SearchEventListener
    private lateinit var searchMviIntent: SearchMviIntent
    private lateinit var observer: Observer<State>
    private lateinit var searchUserByEmailUseCase: SearchUserByEmailUseCase
    private lateinit var schedulerProvider: SchedulerProvider
    private lateinit var testScheduler: TestScheduler
    private lateinit var userViewModelConverter: UserViewModelConverter

    @Before
    fun setUp() {
        searchEventListener = spyk()
        observer = spyk()
        searchUserByEmailUseCase = spyk()
        schedulerProvider = spyk()
        userViewModelConverter = spyk()
        testScheduler = TestScheduler()
        every {
            schedulerProvider.io()
        } returns testScheduler
        searchMviIntent =
            SearchMviIntent(
                searchEventListener,
                searchUserByEmailUseCase,
                schedulerProvider,
                userViewModelConverter
            )
        searchMviIntent.observableState.observeForever(observer)
    }

    @Test
    fun `Should goBack when that action was sent`() {
        //Given
        val expectedState = State(idle = true)

        //When
        searchMviIntent.dispatch(Action.GoBack)
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            observer.onChanged(expectedState)
            searchEventListener.onBackClick()
        }
    }

    @Test
    fun `Should show empty state if there is empty list of users`() {
        //Given
        val searchRequest = "123123@asdasd.com"
        val expectedEmptyState = State(
            emptyList(),
            false,
            null,
            false
        )
        val loaderState = State(
            emptyList(),
            true,
            null,
            false
        )
        every {
            searchUserByEmailUseCase(any(), any())
        } returns Observable.empty()

        //When
        searchMviIntent.dispatch(Action.Search(searchRequest))
        testSchedulerRule.triggerActions()
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        //Then
        verifyOrder {
            observer.onChanged(loaderState)
            observer.onChanged(expectedEmptyState)
        }
    }

    @Test
    fun `Should show content if there is list of user is not empty`() {
        //Given
        val searchRequest = "123123@asdasd.com"
        val viewModelUser: UserViewModel = mockk()
        val user: User = mockk()
        val contentState = State(
            listOf(viewModelUser),
            false,
            null,
            false
        )
        val loaderState = State(
            emptyList(),
            true,
            null,
            false
        )
        every {
            searchUserByEmailUseCase(any(), any())
        } returns Observable.just(arrayListOf(user))
        every {
            userViewModelConverter.convert(any())
        } returns viewModelUser

        //When
        searchMviIntent.dispatch(Action.Search(searchRequest))
        testSchedulerRule.triggerActions()
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        //Then
        verifyOrder {
            observer.onChanged(loaderState)
            observer.onChanged(contentState)
        }
    }
}