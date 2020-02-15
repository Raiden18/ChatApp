package com.raiden.login.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.raiden.core.extensions.addTextChangedListener
import com.raiden.core.extensions.hideKeyboard
import com.raiden.core.extensions.setOnclickListener
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.core.widgets.dialogs.ShowErrorDialog
import com.raiden.login.R
import com.raiden.login.intent.MviIntent
import com.raiden.login.models.Action
import com.raiden.login.models.State
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : CoreMviFragment<Action, State>(R.layout.fragment_login) {
    private val showErrorDialog: ShowErrorDialog by currentScope.inject()

    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<MviIntent>(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        log_in_field_edit_text.addTextChangedListener { email ->
            mviIntent.dispatch(Action.InputLogIn(email))
        }
        password_field_edit_text.addTextChangedListener {
            mviIntent.dispatch(Action.InputPassword(it))
        }
        log_in_button.setOnclickListener {
            mviIntent.dispatch(Action.LogIn)
        }
    }

    override fun renderState(state: State) {
        with(state) {
            when {
                error != null -> bindErrorState(error)
                isShowLoader -> bindLoaderState()
                else -> bindContentState(state)
            }
        }
    }

    private fun bindErrorState(error: Throwable) {
        showErrorDialog.show(requireActivity(), error)
        login_loader.visibleChildId = R.id.login_content
    }

    private fun bindLoaderState() {
        view?.hideKeyboard()
        login_loader.showProgress()
    }

    private fun bindContentState(state: State) {
        log_in_field_layout.renderState(state.logInState, R.string.wrong_login)
        password_field_layout.renderState(state.passwordState, R.string.wrong_password)
        log_in_button.isEnabled = state.isButtonEnabled
        login_loader.visibleChildId = R.id.login_content
    }

    override fun initAction(): Action = Action.ShowEmpty
}