package com.raiden.core.mvi

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class CoreMviIntent<A : CoreAction, S : CoreState> : ViewModel() {
    protected val actions: PublishSubject<A> = PublishSubject.create<A>()
    protected val disposables: CompositeDisposable = CompositeDisposable()
    protected abstract val initialState: S
    protected val state = BehaviorRelay.create<S>()

    val observableState: Observable<S> = state

    protected abstract fun bindActions()

    fun dispatch(action: A) {
        MviLogger.log("HUI: Received action: $action")
        actions.onNext(action)
    }

    override fun onCleared() {
        disposables.clear()
    }
}