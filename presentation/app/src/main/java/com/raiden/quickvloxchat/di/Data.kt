package com.raiden.quickvloxchat.di

import com.raiden.data.frameworks.quickblox.adapters.users.QBUsersRxAdapter
import com.raiden.data.frameworks.quickblox.adapters.users.QBUsersRxAdapterImpl
import com.raiden.data.frameworks.quickblox.QuickbloxInitter
import com.raiden.data.repositories.ChatRepositoryImpl
import com.raiden.data.repositories.ChatRoomRepositoryImpl
import com.raiden.domain.repositories.ChatRepository
import com.raiden.domain.repositories.ChatRoomRepository
import org.koin.dsl.module

val DATA_DEPENDENCIES = module(createdAtStart = true) {
    single { QuickbloxInitter(get()) }
    //Adapters
    single<QBUsersRxAdapter> { QBUsersRxAdapterImpl() }

    //Repositories
    single<ChatRepository> { ChatRepositoryImpl(get()) }
    single<ChatRoomRepository> { ChatRoomRepositoryImpl() }

}