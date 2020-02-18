package com.raiden.search.intent

import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.Reducer
import com.raiden.core.utils.rx.schedule.SchedulerProvider
import com.raiden.domain.usecases.search.SearchUserByEmailUseCase
import com.raiden.search.models.Action
import com.raiden.search.models.Change
import com.raiden.search.models.State
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchMviIntent(
    private val searchEventListener: SearchEventListener,
    private val searchUserByEmailUseCase: SearchUserByEmailUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val userViewModelConverter: UserViewModelConverter
) : CoreMviIntent<Action, State>() {
    override val initialState: State = State.Idle
    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Idle -> State.Idle
            is Change.ShowLoader -> State.LoaderState
            is Change.EmptySearchResult -> State.EmptyState
            is Change.ShowContent -> State.ContentState(change.users)
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
            .debounce(350, TimeUnit.MILLISECONDS, schedulerProvider.io())
            .switchMap<Change> { email ->
                if (email.isEmpty())
                    Observable.just(Change.Idle)
                else
                    searchUserByEmailUseCase.invoke(email, 0) // Implement pager
                        .flatMap { Observable.fromIterable(it) }
                        .map { userViewModelConverter.convert(it) }
                        .toList()
                        .toObservable()
                        .filter { it.isNotEmpty() }
                        .map<Change> { Change.ShowContent(it) }
                        .defaultIfEmpty(Change.EmptySearchResult)
                        .startWith(Change.ShowLoader)
            }

        disposables += Observable.merge(goBack, searchUsers, idle)
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .subscribe(state::postValue, Timber::e)
    }
}