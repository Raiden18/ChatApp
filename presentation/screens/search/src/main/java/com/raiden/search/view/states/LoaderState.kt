package com.raiden.search.view.states

import com.raiden.search.view.SearchFragment

internal class LoaderState(
    fragment: SearchFragment
) : AbstractState(fragment) {

    override fun render() {
        loader.show()
    }
}