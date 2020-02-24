package com.raiden.search.view.states

import com.raiden.search.view.SearchFragment

internal class ErrorState(
    fragment: SearchFragment,
    private val error: Throwable
) : AbstractState(fragment) {

    override fun render() {

    }
}