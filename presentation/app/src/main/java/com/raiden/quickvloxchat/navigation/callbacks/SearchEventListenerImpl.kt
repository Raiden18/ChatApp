package com.raiden.quickvloxchat.navigation.callbacks

import com.raiden.quickvloxchat.navigation.screens.Screen
import com.raiden.search.intent.SearchEventListener
import ru.terrakok.cicerone.Router

class SearchEventListenerImpl(
    private val router: Router
) : SearchEventListener {

    override fun onBackClick() {
        router.exit()
    }

    override fun onUserClick(userName: String) {
        val chatClick = Screen.ChatRoom(userName)
        router.navigateTo(chatClick)
    }
}