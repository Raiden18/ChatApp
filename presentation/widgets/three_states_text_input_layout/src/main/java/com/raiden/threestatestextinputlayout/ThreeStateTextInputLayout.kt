package com.raiden.threestatestextinputlayout

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class ThreeStateTextInputLayout(
    context: Context,
    attributeSet: AttributeSet
) : TextInputLayout(context, attributeSet) {

    fun renderState(fieldState: FieldState, errorIdString: Int) {
        when (fieldState) {
            FieldState.InvalidState -> {
                error = context.getString(errorIdString)
                setDrawableRight(0)
            }
            FieldState.ValidState -> {
                error = null
                editText!!.setDrawableRight(R.drawable.ic_check)
            }
            FieldState.EmptyState -> {
                error = null
                setDrawableRight(0)
            }
        }
    }

    private fun setDrawableRight(drawable: Int){
        editText?.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    }
}