package com.raiden.core.widgets.loader

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ViewAnimator
import com.raiden.core.R

 open class BetterViewAnimator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewAnimator(context, attrs) {

    init {
        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.BetterViewAnimator, 0, 0
            )
            if (typedArray.hasValue(R.styleable.BetterViewAnimator_visible_child)) {
                visibleChildId = typedArray
                    .getResourceId(R.styleable.BetterViewAnimator_visible_child, 0)
            }
            typedArray.recycle()
        }
    }

    var visibleChildId: Int
        get() = getChildAt(displayedChild).id
        set(id) {
            if (visibleChildId == id) {
                return
            }
            var i = 0
            val count = childCount
            while (i < count) {
                val childView = getChildAt(i)
                if (childView.id == id) {
                    displayedChild = i
                    return
                }
                if (childView.id == R.id.view_animator_progress_view) {
                    childView.visibility = View.GONE
                }
                i++
            }
            throw IllegalArgumentException("No view with ID $id")
        }

}
