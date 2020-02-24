package com.raiden.search.view

import com.raiden.domain.models.User
import com.raiden.search.intent.UserViewModelConverter
import com.raiden.search.models.UserViewModel

class UserViewModelConverterImpl : UserViewModelConverter {

    override fun convert(user: User): UserViewModel {
        val firstLetter = user.fullName.first().toString().toUpperCase()
        return UserViewModel(firstLetter, user)
    }
}