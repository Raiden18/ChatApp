package com.raiden.quickvloxchat.di

import com.raiden.domain.usecases.chatroom.messages.get.GetMessagesHistory
import com.raiden.domain.usecases.chatroom.messages.get.GetMessagesHistoryImpl
import com.raiden.domain.usecases.chatroom.user.get.GetSelectedUserForChat
import com.raiden.domain.usecases.chatroom.user.get.GetSelectedUserForChatImpl
import com.raiden.domain.usecases.chatroom.user.select.SelectUserForChat
import com.raiden.domain.usecases.chatroom.user.select.SelectUserForChatImpl
import com.raiden.domain.usecases.chats.get.GetAllChatsUseCase
import com.raiden.domain.usecases.chats.get.GetAllChatsUseCaseImpl
import com.raiden.domain.usecases.login.LogInUseCase
import com.raiden.domain.usecases.login.LogInUseCaseImpl
import com.raiden.domain.usecases.search.SearchUserByEmailUseCase
import com.raiden.domain.usecases.search.SearchUserByEmailUseCaseImpl
import org.koin.dsl.module

val DOMAIN_DEPENDENCIES = module(createdAtStart = true) {
    single<LogInUseCase> { LogInUseCaseImpl(get()) }
    single<GetAllChatsUseCase> { GetAllChatsUseCaseImpl() }
    single<SearchUserByEmailUseCase> { SearchUserByEmailUseCaseImpl(get()) }
    single<GetMessagesHistory> { GetMessagesHistoryImpl() }
    single<SelectUserForChat> { SelectUserForChatImpl(get()) }
    single<GetSelectedUserForChat> { GetSelectedUserForChatImpl(get()) }
}