package com.raiden.quickvloxchat.navigation.screens

import androidx.fragment.app.Fragment
import com.raiden.login.LogInFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screen(private val fragment: Fragment) : SupportAppScreen() {
    override fun getFragment(): Fragment = fragment

    class LogIn : Screen(LogInFragment())
}