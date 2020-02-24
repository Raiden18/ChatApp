package com.raiden.quickvloxchat.di

import com.raiden.data.frameworks.quickblox.facades.ChatRoomFacadeImpl
import com.raiden.data.frameworks.quickblox.QuickbloxInitter
import com.raiden.data.frameworks.quickblox.adapters.QBRxAdapter
import com.raiden.data.frameworks.quickblox.adapters.QBRxAdapterImpl
import com.raiden.data.repositories.ChatRepositoryImpl
import com.raiden.data.repositories.SessionRepositoryImpl
import com.raiden.data.repositories.chatroom.ChatRoomFacade
import com.raiden.data.repositories.chatroom.ChatRoomRepositoryImpl
import com.raiden.domain.repositories.ChatRepository
import com.raiden.domain.repositories.ChatRoomRepository
import com.raiden.domain.repositories.SessionRepository
import org.koin.dsl.module

val DATA_DEPENDENCIES = module(createdAtStart = true) {
    single { QuickbloxInitter(get()) }
    //Adapters
    single<QBRxAdapter> { QBRxAdapterImpl() }

    //Repositories
    single<ChatRepository> { ChatRepositoryImpl(get()) }
    single<ChatRoomRepository> { ChatRoomRepositoryImpl(get()) }
    single<SessionRepository> { SessionRepositoryImpl(get()) }
    single<ChatRoomFacade> { ChatRoomFacadeImpl(get()) }
}