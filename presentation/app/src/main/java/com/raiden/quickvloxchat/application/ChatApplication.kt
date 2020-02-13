package com.raiden.quickvloxchat.application

import android.app.Application
import com.raiden.core.mvi.MviLogger
import com.raiden.login.di.LOG_IN_MODULE
import com.raiden.quickvloxchat.activity.MAIN_ACTIVITY_MODULE
import com.raiden.quickvloxchat.di.DATA_DEPENDENCIES
import com.raiden.quickvloxchat.di.DOMAIN_DEPENDENCIES
import com.raiden.quickvloxchat.di.PRESENTATION_DEPENDENCIES
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

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
                    PRESENTATION_DEPENDENCIES,
                    MAIN_ACTIVITY_MODULE,
                    LOG_IN_MODULE
                )
            )
        }
    }

    private fun initLogger(){
        MviLogger.enableLogging(object : MviLogger.Logger {
            override fun log(msg: String) {
                Timber.tag("MVI").d(msg)
            }
        })

    }
}