package com.raiden.quickvloxchat.navigation.callbacks

import com.raiden.chats.intent.ChatsEventListener
import com.raiden.quickvloxchat.navigation.screens.Screen
import ru.terrakok.cicerone.Router

class ChatsEventListenerImpl(
    private val router: Router
) : ChatsEventListener {

    override fun onSearchClick() {
        val searchScreen = Screen.Search()
        router.navigateTo(searchScreen)
    }

    override fun goBack() {
        router.exit()
    }
}