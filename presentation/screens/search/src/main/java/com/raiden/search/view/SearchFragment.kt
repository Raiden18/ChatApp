package com.raiden.search.view

import android.os.Bundle
import android.util.Log
import android.view.View
import com.raiden.core.extensions.addTextChangedListener
import com.raiden.core.extensions.showKeyboard
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.search.R
import com.raiden.search.intent.SearchMviIntent
import com.raiden.search.models.Action
import com.raiden.search.models.State
import com.raiden.search.models.UserViewModel
import com.raiden.search.view.widgets.emtymessage.animateAppearanceAndDo
import com.raiden.search.view.widgets.emtymessage.animateHidingAndDo
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
        when (state) {
            is State.Idle -> renderIdle()
            is State.ContentState -> renderContent(state.users)
            is State.LoaderState -> renderLoader()
            is State.ErrorState -> renderError()
            is State.EmptyState -> renderEmptyContent()
        }
    }

    private fun renderLoader() {
        search_loader.visibility = View.VISIBLE
    }

    private fun renderEmptyContent() {
        search_empty_message_container.animateAppearanceAndDo {
            hideUsers()
            hideLoader()
        }
    }

    private fun renderContent(users: List<UserViewModel>) {
        if (search_empty_message_container.alpha == 0f) {
            hideLoader()
            search_users.updateUsers(users)
        } else {
            search_empty_message_container.animateHidingAndDo {
                hideLoader()
                search_users.updateUsers(users)
            }
        }
    }

    private fun renderError() {
        hideLoader()
    }

    private fun renderIdle() {
        hideLoader()
        search_empty_message_container.animateHidingAndDo {
            hideUsers()
        }
    }

    override fun initAction(): Action? = null

    private fun hideLoader() {
        search_loader.visibility = View.INVISIBLE
    }

    private fun hideUsers() {
        search_users.updateUsers(emptyList())
    }
}


