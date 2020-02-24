package com.raiden.chats.di

import com.raiden.chats.intent.ChatsMviIntent
import com.raiden.chats.view.ChatsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val CHATS_MODULE = module {
    scope(named<ChatsFragment>()) {
        viewModel { ChatsMviIntent(get(), get()) }
    }
}