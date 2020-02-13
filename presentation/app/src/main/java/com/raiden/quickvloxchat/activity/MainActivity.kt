package com.raiden.quickvloxchat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raiden.quickvloxchat.R
import com.raiden.quickvloxchat.navigation.screens.Screen
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

//TODO: implement MVI
class MainActivity : AppCompatActivity() {
    private val cicerone: Cicerone<Router> by inject()
    private val router: Router by inject()
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = SupportAppNavigator(this, R.id.host_fragment)
        router.navigateTo(Screen.LogIn())
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }
}
