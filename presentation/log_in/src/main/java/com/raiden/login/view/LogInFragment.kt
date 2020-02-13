package com.raiden.login.view

import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.mvi.ViewState
import com.raiden.login.R
import com.raiden.login.intent.MviIntent
import com.raiden.login.intent.states.SomeState
import com.raiden.login.models.Action
import com.raiden.login.models.State
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : CoreMviFragment<Action, State>(R.layout.fragment_login) {
    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<MviIntent>(this)

    override fun renderState(state: State): ViewState {
        return SomeState()
    }

    override fun initAction(): Action = Action.ShowEmpty
}