package com.raiden.quickvloxchat.di

import com.raiden.domain.usecases.chat.get.GetAllChatsUseCase
import com.raiden.domain.usecases.chat.get.GetAllChatsUseCaseImpl
import com.raiden.domain.usecases.login.LogInUseCase
import com.raiden.domain.usecases.login.LogInUseCaseImpl
import org.koin.dsl.module

val DOMAIN_DEPENDENCIES = module(createdAtStart = true) {
    single<LogInUseCase> { LogInUseCaseImpl(get()) }
    single<GetAllChatsUseCase> { GetAllChatsUseCaseImpl() }
}