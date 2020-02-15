package com.raiden.login.intent.states

import android.util.Log
import com.raiden.core.mvi.ViewState

class SomeState : ViewState {

    override fun render() {
        Log.i("HUI", "RENDER")
    }
}