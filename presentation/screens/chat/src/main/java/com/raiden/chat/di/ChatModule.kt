package com.raiden.chat.di

import com.raiden.chat.intent.ChatMviIntent
import com.raiden.chat.view.ChatFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val CHAT_MODULE = module {
    scope(named<ChatFragment>()) {
        viewModel {
            ChatMviIntent()
        }
    }
}