package com.raiden.quickvloxchat.application

import android.app.Application
import com.raiden.quickvloxchat.di.DATA_DEPENDENCIES
import com.raiden.quickvloxchat.di.DOMAIN_DEPENDENCIES
import com.raiden.quickvloxchat.di.PRESENTATION_DEPENDENCIES
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ChatApplication)
            modules(
                listOf(
                    DOMAIN_DEPENDENCIES,
                    DATA_DEPENDENCIES,
                    PRESENTATION_DEPENDENCIES
                )
            )
        }

    }
}