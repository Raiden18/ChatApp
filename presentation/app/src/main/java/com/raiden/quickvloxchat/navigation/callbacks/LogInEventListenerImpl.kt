package com.raiden.quickvloxchat.navigation.callbacks

import com.raiden.login.intent.LogInEventListener
import com.raiden.quickvloxchat.navigation.screens.Screen
import ru.terrakok.cicerone.Router

class LogInEventListenerImpl(
    private val router: Router
) : LogInEventListener {

    override fun onLogInClick() {
        router.navigateTo(Screen.Chats())
    }
}