package com.raiden.core.widgets.dialogs

import android.app.Activity

interface ShowErrorDialog {
    fun show(activity: Activity, throwable: Throwable)
}