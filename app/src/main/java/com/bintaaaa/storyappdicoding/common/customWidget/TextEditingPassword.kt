package com.bintaaaa.storyappdicoding.common.customWidget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class TextEditingPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        this.transformationMethod = PasswordTransformationMethod.getInstance()
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



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Password minimal 8 karakter"
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        val border = GradientDrawable()
        border.setStroke(2,android.graphics.Color.BLUE)
        border.cornerRadius = 16f

        this.background = border
    }


    private fun errorMessage(){
         if(text.toString().length < 8){
             error =  "Password minimal 8 karakter"
        }
    }
}