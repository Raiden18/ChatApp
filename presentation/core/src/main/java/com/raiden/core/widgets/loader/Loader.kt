package com.raiden.core.widgets.loader

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.children
import com.raiden.core.R

class Loader(
    context: Context,
    attributeSet: AttributeSet
) : BetterViewAnimator(context, attributeSet){
    init {
        LayoutInflater.from(getContext()).inflate(R.layout.chat_loader, this)
        enableOnVisibilityChangesTransitionAnimation()
    }

    fun showProgress() {
        children.findLast { it.id == R.id.view_animator_progress_view }?.visibility =
            View.VISIBLE
        visibleChildId = R.id.view_animator_progress_view
    }

    private fun enableOnVisibilityChangesTransitionAnimation() {
        val layoutTransition = LayoutTransition()
        layoutTransition.enableOnlyVisibilityChangesType()
        setLayoutTransition(layoutTransition)
    }

    private fun LayoutTransition.enableOnlyVisibilityChangesType() {
        disableTransitionType(LayoutTransition.CHANGE_APPEARING)
        disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
    }
}