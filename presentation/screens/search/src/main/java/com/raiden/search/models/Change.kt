package com.raiden.search.models

sealed class Change {
    object Idle : Change()
    data class ShowContent(val users: List<UserViewModel>): Change()
    object EmptySearchResult: Change()
    object ShowLoader: Change()
}