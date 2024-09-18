package com.bintaaaa.storyappdicoding.common.customWidget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bintaaaa.storyappdicoding.R

class TextEditingPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    init {

    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            errorMessage()
        }

        override fun afterTextChanged(s: Editable) {
        }
     })
    }



    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        val border = GradientDrawable()
        border.setStroke(2, ContextCompat.getColor(context, R.color.softBlue))
        border.cornerRadius = 16f
        this.background = border
    }


    private fun errorMessage(){
        val isPassword: Boolean = inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        if (isPassword) {
            if(text.toString().length < 8){
                error =  "Password minimal 8 karakter"
            }
        }
    }
}