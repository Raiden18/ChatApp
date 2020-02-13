package com.raiden.core.mvi

typealias Reducer<S, C> = (state: S, change: C) -> S