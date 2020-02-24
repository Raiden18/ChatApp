package com.raiden.search.models

import com.raiden.domain.models.User

data class UserViewModel(
    val firstLetter: String,
    val user: User
)