package com.raiden.core.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class CoreMviIntent<A : CoreAction, S : CoreState>: ViewModel(){
    protected val actions: PublishSubject<A> = PublishSubject.create<A>()
    protected val disposables: CompositeDisposable = CompositeDisposable()
    protected abstract val initialState: S
    protected val state = MutableLiveData<S>()

    val observableState: LiveData<S> = MediatorLiveData<S>().apply {
        addSource(state) { data ->
            MviLogger.log("MVI: Received state: $data")
            setValue(data)
        }
    }

    abstract fun bindActions()

    fun dispatch(action: A) {
        MviLogger.log("MVI: Received action: $action")
        actions.onNext(action)
    }

    override fun onCleared() {
        disposables.clear()
    }
}