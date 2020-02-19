package com.raiden.core.utils.ui

import android.animation.Animator

class SimpleAnimationListener private constructor(
    private var onAnimationEnd: (() -> Unit)?,
    private var onAnimationStart: (() -> Unit)?
) : Animator.AnimatorListener {

    companion object {
        fun onAnimationEnd(onAnimationEnd: () -> Unit): SimpleAnimationListener {
            return SimpleAnimationListener(onAnimationEnd, null)
        }

        fun onAnimationStart(onAnimationStart: () -> Unit): SimpleAnimationListener {
            return SimpleAnimationListener(null, onAnimationStart)
        }
    }

    override fun onAnimationRepeat(p0: Animator?) {

    }

    override fun onAnimationEnd(p0: Animator?) {
        onAnimationEnd?.let {
            it()
        }
    }

    override fun onAnimationCancel(p0: Animator?) {

    }

    override fun onAnimationStart(p0: Animator?) {
        onAnimationStart?.let {
            it()
        }
    }
}