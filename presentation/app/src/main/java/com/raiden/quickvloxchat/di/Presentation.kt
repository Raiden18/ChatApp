package com.raiden.quickvloxchat.di

import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val PRESENTATION_DEPENDENCIES = module {
    single { Router() }
    single<Cicerone<Router>> { Cicerone.create(get()) }
}