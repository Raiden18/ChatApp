package com.raiden.threestatestextinputlayout

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class ThreeStatesTextInputLayout(
    context: Context,
    attributeSet: AttributeSet
) : TextInputLayout(context, attributeSet) {

    fun renderState(fieldState: FieldState, errorIdString: Int) {
        error = when (fieldState) {
            FieldState.InvalidState -> {
                context.getString(errorIdString)
            }
            else -> {
                null
            }
        }
    }

    private fun setDrawableRight(drawable: Int) {
        editText?.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    }
}