package com.raiden.search.view.states

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.raiden.core.mvi.ViewState
import com.raiden.search.view.SearchFragment
import kotlinx.android.synthetic.main.fragment_search.*

internal abstract class AbstractState(
    protected val fragment: SearchFragment
) : ViewState {
    protected val usersRecyclerView = fragment.search_users
    protected val emptyMessage = fragment.search_empty_message_container
    protected val loader = fragment.search_loader

    protected fun ProgressBar.hide() {
        visibility = View.INVISIBLE
    }

    protected fun ProgressBar.show() {
        visibility = View.VISIBLE
    }


    protected fun LinearLayout.isHidden(): Boolean {
        return alpha == 0f
    }
}