package com.raiden.quickvloxchat.navigation.callbacks

import com.raiden.search.intent.SearchEventListener
import ru.terrakok.cicerone.Router

class SearchEventListenerImpl(
    private val router: Router
) : SearchEventListener {

    override fun onBackClick() {
        router.exit()
    }
}