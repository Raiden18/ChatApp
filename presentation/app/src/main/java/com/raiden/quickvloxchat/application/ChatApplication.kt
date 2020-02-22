package com.raiden.quickvloxchat.application

import android.app.Application
import com.raiden.chat.di.CHAT_ROOM_MODULE
import com.raiden.chats.di.CHATS_MODULE
import com.raiden.core.mvi.MviLogger
import com.raiden.login.di.LOG_IN_MODULE
import com.raiden.quickvloxchat.activity.MAIN_ACTIVITY_MODULE
import com.raiden.quickvloxchat.di.DATA_DEPENDENCIES
import com.raiden.quickvloxchat.di.DOMAIN_DEPENDENCIES
import com.raiden.quickvloxchat.di.NAVIGATION_MODULE
import com.raiden.quickvloxchat.di.PRESENTATION_DEPENDENCIES
import com.raiden.search.di.SEARCH_MODULE
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        Timber.plant(Timber.DebugTree())
        initLogger()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ChatApplication)
            modules(
                listOf(
                    DOMAIN_DEPENDENCIES,
                    DATA_DEPENDENCIES,
                    PRESENTATION_DEPENDENCIES,
                    NAVIGATION_MODULE,
                    MAIN_ACTIVITY_MODULE,
                    LOG_IN_MODULE,
                    CHATS_MODULE,
                    SEARCH_MODULE,
                    CHAT_ROOM_MODULE
                )
            )
        }
    }

    private fun initLogger() {
        MviLogger.enableLogging(object : MviLogger.Logger {
            override fun log(msg: String) {
                Timber.tag("HUI").d(msg)
            }
        })

    }
}