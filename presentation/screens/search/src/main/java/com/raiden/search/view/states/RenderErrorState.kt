package com.raiden.search.view.states

import com.raiden.search.view.SearchFragment

internal class RenderErrorState(
    fragment: SearchFragment
) : AbstractState(fragment) {

    override fun render() {
        loader.hide()
        usersRecyclerView.hide()
        loader.hide()
    }
}