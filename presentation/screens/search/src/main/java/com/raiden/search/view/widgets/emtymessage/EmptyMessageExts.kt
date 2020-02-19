package com.raiden.search.view.widgets.emtymessage

import android.widget.LinearLayout
import com.raiden.core.utils.ui.SimpleAnimationListener

private const val ANIMATION_DURATION = 400L

internal fun LinearLayout.animateAppearanceAndDo(doOnStart: () -> Unit) {
    animate()
        .alpha(1f)
        .setDuration(ANIMATION_DURATION)
        .setListener(SimpleAnimationListener.onAnimationStart(doOnStart))
        .start()
}

internal fun LinearLayout.animateAppearance() {
    animate()
        .alpha(1f)
        .setDuration(ANIMATION_DURATION)
        .start()
}

/*------------------------------------------------------------------------*/

internal fun LinearLayout.animateHidingAndDo(doOnEnd: () -> Unit) {
    animate()
        .alpha(0f)
        .setDuration(ANIMATION_DURATION)
        .setListener(SimpleAnimationListener.onAnimationEnd(doOnEnd))
        .start()
}

internal fun LinearLayout.animateHiding() {
    animate()
        .alpha(0f)
        .setDuration(ANIMATION_DURATION)
        .start()
}