package com.example.permission_helper.ui.view_touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button

class ButtonA : Button {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("ViewTouch btnA onTouchEvent")
  return super.onTouchEvent(event)

    }
}