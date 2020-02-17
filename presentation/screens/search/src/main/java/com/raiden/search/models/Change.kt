package com.raiden.search.models

import com.raiden.domain.models.User

sealed class Change {
    object Idle : Change()
    data class ShowContent(val users: List<UserViewModel>): Change()
    object EmptySearchResult: Change()
    object ShowLoader: Change()
}