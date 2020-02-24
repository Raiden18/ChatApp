package com.raiden.core.mvi

import android.util.Log

object MviLogger {

    private var logger: Logger? = null

    /**
     * This enables logging of Actions. If [DefaultLogger] is used, logging will be done using [println].
     */
    fun enableLogging(logger: Logger = DefaultLogger()) {
        this.logger = logger
    }

    internal fun log(msg: String) {
        logger?.log(msg)
    }

    interface Logger {
        fun log(msg: String)
    }

    private class DefaultLogger : Logger {
        override fun log(msg: String) {
            Log.i("MVI", msg)
        }
    }
}
