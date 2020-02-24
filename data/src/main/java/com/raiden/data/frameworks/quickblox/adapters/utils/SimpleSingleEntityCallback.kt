package com.raiden.data.frameworks.quickblox.adapters.utils

import android.os.Bundle
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import io.reactivex.SingleEmitter

class SimpleSingleEntityCallback<T>(
    private val singleEmitter: SingleEmitter<T>
) : QBEntityCallback<T> {

    override fun onSuccess(p0: T?, p1: Bundle?) {
        p0?.let {
            singleEmitter.onSuccess(p0)
        }
    }

    override fun onError(p0: QBResponseException?) {
        p0?.let {
            singleEmitter.onError(Exception(p0))
        }
    }
}