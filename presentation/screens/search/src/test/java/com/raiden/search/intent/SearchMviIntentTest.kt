package com.raiden.search.intent

import androidx.lifecycle.Observer
import com.livetyping.beautyshop.core.testutils.BaseMviIntentTest
import com.raiden.domain.usecases.search.SearchUserByEmailUseCase
import com.raiden.search.models.Action
import com.raiden.search.models.State
import io.mockk.every
import io.mockk.spyk
import io.mockk.verifyOrder
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class SearchMviIntentTest : BaseMviIntentTest() {

    private lateinit var searchEventListener: SearchEventListener
    private lateinit var searchMviIntent: SearchMviIntent
    private lateinit var observer: Observer<State>
    private lateinit var searchUserByEmailUseCase: SearchUserByEmailUseCase
    @Before
    fun setUp() {
        searchEventListener = spyk()
        observer = spyk()
        searchUserByEmailUseCase = spyk()
        searchMviIntent = SearchMviIntent(searchEventListener, searchUserByEmailUseCase)
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
            searchUserByEmailUseCase(any())
        } returns Observable.empty()

        //When
        searchMviIntent.dispatch(Action.Search(searchRequest))
        testSchedulerRule.triggerActions()

        //Then
        verifyOrder {
            observer.onChanged(loaderState)
            observer.onChanged(expectedEmptyState)
        }

    }
}