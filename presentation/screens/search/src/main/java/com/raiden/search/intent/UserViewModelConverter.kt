package com.raiden.search.intent

import com.raiden.domain.models.User
import com.raiden.search.models.UserViewModel

interface UserViewModelConverter {
    fun convert(user: User): UserViewModel
}