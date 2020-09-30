package com.example.permission_helper.ui.view_touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View


class viewCustom : android.support.v7.widget.AppCompatImageView, View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        println("viewCustom.onTouch")
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("viewCustom.onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        println("viewCustom.onScaleBegin")
        return false
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        println("viewCustom.onScaleEnd")
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        println("viewCustom.onScale")

        return true
    }
}