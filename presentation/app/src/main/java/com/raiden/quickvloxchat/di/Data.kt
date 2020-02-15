package com.raiden.quickvloxchat.di

import com.raiden.data.frameworks.adapters.users.QBUsersRxAdapter
import com.raiden.data.frameworks.adapters.users.QBUsersRxAdapterImpl
import com.raiden.data.frameworks.quickblox.QuickbloxInitter
import com.raiden.data.gateways.ChatGatewayImpl
import com.raiden.domain.repositories.ChatGateway
import org.koin.dsl.module

val DATA_DEPENDENCIES = module(createdAtStart = true) {
    single { QuickbloxInitter(get()) }
    //Adapters
    single<QBUsersRxAdapter> { QBUsersRxAdapterImpl() }
    //Gateways
    single<ChatGateway> { ChatGatewayImpl(get()) }
}