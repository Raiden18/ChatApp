package com.raiden.search.view

import android.os.Bundle
import android.view.View
import com.raiden.core.extensions.addTextChangedListener
import com.raiden.core.extensions.showKeyboard
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.ViewState
import com.raiden.search.R
import com.raiden.search.intent.SearchMviIntent
import com.raiden.search.models.Action
import com.raiden.search.models.State
import com.raiden.search.view.states.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : CoreMviFragment<Action, State>(R.layout.fragment_search) {
    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<SearchMviIntent>(
        this
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_edit_tet.requestFocus()
        search_edit_tet.showKeyboard()
        search_toolbar.setNavigationOnClickListener { mviIntent.dispatch(Action.GoBack) }
        search_edit_tet.addTextChangedListener { mviIntent.dispatch(Action.Search(it)) }
        search_users.onUserClick = { mviIntent.dispatch(Action.SelectUser(it)) }
    }

    override fun renderState(state: State) {
        val viewState = stateFabric(state)
        viewState.render()
    }

    private fun stateFabric(state: State): ViewState {
        return when (state) {
            is State.Idle -> IdleState(this)
            is State.ContentState -> ContentState(this, state.users)
            is State.LoaderState -> LoaderState(this)
            is State.ErrorState -> ErrorState(this, state.throwable)
            is State.EmptyState -> EmptyState(this)
        }
    }

    override fun initAction(): Action? = null
}


