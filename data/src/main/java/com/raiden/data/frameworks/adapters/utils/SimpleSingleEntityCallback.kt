package com.raiden.data.frameworks.adapters.utils

import android.os.Bundle
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import io.reactivex.SingleEmitter

class SimpleSingleEntityCallback<T>(
    private val singleEmitter: SingleEmitter<T>
) : QBEntityCallback<T> {
    override fun onSuccess(p0: T, p1: Bundle?) {
        singleEmitter.onSuccess(p0)
    }

    override fun onError(p0: QBResponseException?) {
        singleEmitter.onError(Exception(p0))
    }
}