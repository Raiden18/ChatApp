package com.raiden.data.frameworks.quickblox

import android.app.Application
import android.util.Log
import com.quickblox.auth.session.QBSettings
import com.quickblox.chat.QBChatService

class QuickbloxInitter(private val application: Application) {
    companion object {
        const val APP_ID = "80298"
        const val AUTH_KEY = "75aX7kBuGPTR4uq"
        const val AUTH_SECRET = "VPJubkOAPPC6z2V"
        const val ACCOUNT_KEY = "kZ9jcjUVk3waqbzYn76F"

        const val SOCKET_TIMEOUT = 60 * 1000
        const val PACKAGE_REPLY_TIMEOUT = 10 * 1000
    }

    init {
        initSettings()
        initChatService()
    }

    private fun initSettings() {
        QBSettings.getInstance().apply {
            init(application, APP_ID, AUTH_KEY, AUTH_SECRET)
            accountKey = ACCOUNT_KEY
        }
    }

    private fun initChatService() {
        val builder = QBChatService.ConfigurationBuilder().apply {
            socketTimeout = SOCKET_TIMEOUT
            isKeepAlive = true
            isUseTls = true
        }
        QBChatService.setConfigurationBuilder(builder)
        QBChatService.setDebugEnabled(true)
        QBChatService.setDefaultPacketReplyTimeout(PACKAGE_REPLY_TIMEOUT)

    }
}