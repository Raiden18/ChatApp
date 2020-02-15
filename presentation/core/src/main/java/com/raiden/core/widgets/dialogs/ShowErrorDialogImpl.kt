package com.raiden.core.widgets.dialogs

import android.app.Activity
import android.app.AlertDialog

class ShowErrorDialogImpl: ShowErrorDialog {
    override fun show(activity: Activity, throwable: Throwable) {
        AlertDialog.Builder(activity)
            .setMessage(throwable.message)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .show()
    }
}