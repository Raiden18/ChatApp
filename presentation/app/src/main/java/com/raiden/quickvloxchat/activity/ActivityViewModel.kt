package com.raiden.quickvloxchat.activity

import androidx.lifecycle.ViewModel
import com.raiden.quickvloxchat.navigation.screens.Screen
import ru.terrakok.cicerone.Router

class ActivityViewModel(
    private val router: Router
) : ViewModel() {

    init {
        router.navigateTo(Screen.LogIn())
    }

}