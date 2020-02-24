package com.raiden.core.utils.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView


class HorizontalRecyclerDividerItemDecoration : RecyclerView.ItemDecoration {
    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    private var divider: Drawable? = null
    private var padding: Int = 0

    /**
     * Default divider will be used
     */
    constructor(context: Context, padding: Int) {
        val styledAttributes = context.obtainStyledAttributes(ATTRS)
        divider = styledAttributes.getDrawable(0)
        this.padding = padding
        styledAttributes.recycle()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.childCount == 0) return

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            drawDividers(canvas, parent, i)
        }
    }

    private fun drawDividers(canvas: Canvas, parent: RecyclerView, position: Int) {
        val child = parent.getChildAt(position)
        val params = child.layoutParams as RecyclerView.LayoutParams
        val top = child.bottom + params.bottomMargin
        val bottom = top + divider!!.intrinsicHeight
        divider!!.setBounds(padding, top, parent.width, bottom)
        divider!!.draw(canvas)
    }
}