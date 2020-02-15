package com.raiden.core.extensions

import android.view.View

fun <T : View> T.setOnclickListener(block: () -> Unit) = setOnClickListener { block() }
