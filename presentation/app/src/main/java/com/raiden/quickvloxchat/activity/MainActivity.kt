package com.raiden.quickvloxchat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raiden.quickvloxchat.R
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {
    private val cicerone: Cicerone<Router> by inject()
    private val viewModel: ActivityViewModel by currentScope.viewModel(this)
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = SupportAppNavigator(this, R.id.host_fragment)
        viewModel
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
