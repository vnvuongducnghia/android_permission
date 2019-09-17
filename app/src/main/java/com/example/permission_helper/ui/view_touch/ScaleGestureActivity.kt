package com.example.permission_helper.ui.view_touch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.example.permission_helper.R
import kotlinx.android.synthetic.main.activity_scale_gesture.*


class ScaleGestureActivity : AppCompatActivity(), ScaleGestureDetector.OnScaleGestureListener {


    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_gesture)
        mScaleGestureDetector = ScaleGestureDetector(this, this)

        flScale.setOnTouchListener { v, event ->
            println("ScaleGestureActivity.onCreate pointerCount " + event.pointerCount)
            mScaleGestureDetector?.onTouchEvent(event)!!;
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        mScaleFactor *= detector!!.scaleFactor;
        mScaleFactor = 0.1f.coerceAtLeast(mScaleFactor.coerceAtMost(10.0f));
        imageView.scaleX = mScaleFactor;
        imageView.scaleY = mScaleFactor;
        return true;
    }
}
