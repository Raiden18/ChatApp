package com.raiden.quickvloxchat.di

import com.raiden.data.frameworks.quickblox.QuickbloxInitter
import com.raiden.data.frameworks.quickblox.adapters.QBUsersRxAdapter
import com.raiden.data.frameworks.quickblox.adapters.QBUsersRxAdapterImpl
import com.raiden.data.repositories.ChatRepositoryImpl
import com.raiden.data.repositories.ChatRoomRepositoryImpl
import com.raiden.data.repositories.SessionRepositoryImpl
import com.raiden.domain.repositories.ChatRepository
import com.raiden.domain.repositories.ChatRoomRepository
import com.raiden.domain.repositories.SessionRepository
import org.koin.dsl.module

val DATA_DEPENDENCIES = module(createdAtStart = true) {
    single { QuickbloxInitter(get()) }
    //Adapters
    single<QBUsersRxAdapter> { QBUsersRxAdapterImpl() }

    //Repositories
    single<ChatRepository> { ChatRepositoryImpl(get()) }
    single<ChatRoomRepository> { ChatRoomRepositoryImpl(get()) }
    single<SessionRepository> { SessionRepositoryImpl(get()) }
}