package com.raiden.search.intent

import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.core.utils.rx.schedule.SchedulerProvider
import com.raiden.domain.usecases.search.SearchUserByEmailUseCase
import com.raiden.search.models.Action
import com.raiden.search.models.Change
import com.raiden.search.models.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchMviIntent(
    private val searchEventListener: SearchEventListener,
    private val searchUserByEmailUseCase: SearchUserByEmailUseCase,
    private val schedulerProvider: SchedulerProvider
) : CoreMviIntent<Action, State>() {
    override val initialState: State = State(idle = true)
    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Idle -> state.copy(idle = true)
            is Change.ShowLoader -> state.copy(isShowLoader = true, error = null, idle = false)
            is Change.EmptySearchResult -> state.copy(
                users = emptyList(),
                isShowLoader = false,
                error = null,
                idle = false
            )
            is Change.ShowContent -> state.copy(
                users = change.users,
                isShowLoader = false,
                error = null,
                idle = false
            )
        }
    }

    init {
        bindActions()
    }

    override fun bindActions() {
        val goBack = actions.ofType<Action.GoBack>()
            .map { Change.Idle }
            .doOnNext { searchEventListener.onBackClick() }

        val idle = actions.ofType<Action.Idle>()
            .map { Change.Idle }

        val searchUsers = actions.ofType<Action.Search>()
            .map { it.email }
            .debounce(500, TimeUnit.MILLISECONDS, schedulerProvider.io())
            .flatMap {
                searchUserByEmailUseCase.invoke(it)
                    .map<Change> { users -> Change.ShowContent(users) }
                    .defaultIfEmpty(Change.EmptySearchResult)
                    .startWith(Change.ShowLoader)

            }

        disposables += Observable.merge(goBack, searchUsers, idle)
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::setValue, Timber::e)
    }
}