package com.raiden.search.view.states

import com.raiden.search.view.SearchFragment
import com.raiden.search.view.widgets.emtymessage.animateAppearanceAndDo

internal class EmptyState(
    fragment: SearchFragment
) : AbstractState(fragment) {

    override fun render() {
        emptyMessage.animateAppearanceAndDo {
            usersRecyclerView.hide()
            loader.hide()
        }
    }
}