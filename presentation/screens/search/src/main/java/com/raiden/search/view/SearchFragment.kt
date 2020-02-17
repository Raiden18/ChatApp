package com.raiden.search.view

import android.os.Bundle
import android.view.View
import com.raiden.core.extensions.addTextChangedListener
import com.raiden.core.mvi.CoreMviFragment
import com.raiden.core.mvi.CoreMviIntent
import com.raiden.search.R
import com.raiden.search.intent.SearchMviIntent
import com.raiden.search.models.Action
import com.raiden.search.models.State
import com.raiden.search.models.UserViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : CoreMviFragment<Action, State>(R.layout.fragment_search) {
    override val mviIntent: CoreMviIntent<Action, State> by currentScope.viewModel<SearchMviIntent>(
        this
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_toolbar.setNavigationOnClickListener { mviIntent.dispatch(Action.GoBack) }
        search_edit_tet.addTextChangedListener { mviIntent.dispatch(Action.Search(it)) }
        search_users.onUserClick = { } // TODO add dispatch
    }

    override fun renderState(state: State) {
        with(state) {
            when {
                idle -> renderIdle()
                else -> renderChangedContent(state)
            }
        }
    }

    private fun renderChangedContent(state: State) {
        with(state) {
            when {
                users.isEmpty() -> renderEmptyContent()
                users.isNotEmpty() -> renderContent(users)
                isShowLoader -> renderLoader()
                error != null -> renderError()
            }
        }
    }

    private fun renderLoader() {
        hideEmptyMessage()
    }

    private fun renderEmptyContent() {
        search_empty_lentach.visibility = View.VISIBLE
        search_empty_message.visibility = View.VISIBLE
        search_users.visibility = View.INVISIBLE
    }

    private fun renderContent(users: List<UserViewModel>) {
        hideEmptyMessage()
        search_users.visibility = View.VISIBLE
        search_users.updateUsers(users)
    }

    private fun renderError() {

    }

    private fun renderIdle() {

    }

    override fun initAction(): Action? = Action.Idle

    private fun hideEmptyMessage() {
        search_empty_lentach.visibility = View.GONE
        search_empty_message.visibility = View.GONE
    }
}