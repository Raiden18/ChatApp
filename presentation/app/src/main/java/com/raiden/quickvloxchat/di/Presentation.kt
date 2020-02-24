package com.raiden.quickvloxchat.di

import com.raiden.core.utils.rx.schedule.SchedulerProvider
import com.raiden.core.utils.rx.schedule.SchedulerProviderImpl
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val PRESENTATION_DEPENDENCIES = module {
    single { Router() }
    single<Cicerone<Router>> { Cicerone.create(get()) }
    single<SchedulerProvider> { SchedulerProviderImpl() }
}