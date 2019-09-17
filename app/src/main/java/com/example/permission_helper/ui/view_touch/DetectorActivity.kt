package com.example.permission_helper.ui.view_touch

import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.permission_helper.R


class DetectorActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {


    private val DEBUG_TAG: String? = "MyLog"
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detector)
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = GestureDetectorCompat(this, this)
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this)
    }

    // onTouchEvent at activity
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        this.mDetector.onTouchEvent(event);
        when (event!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.w(DEBUG_TAG, "Actifon was DOWN")
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.w(DEBUG_TAG, "Action was MOVE")
                return true
            }
            MotionEvent.ACTION_UP -> {
                Log.w(DEBUG_TAG, "Action was UP")
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.w(DEBUG_TAG, "Action was CANCEL")
                return true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.w(DEBUG_TAG, "Movement occurred outside bounds " + "of current screen element")
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }


    // GestureDetector onGestureListener
    override fun onShowPress(e: MotionEvent?) {
        Log.w(DEBUG_TAG, "GestureDetector onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.w(DEBUG_TAG, "GestureDetector onSingleTapUp")
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.w(DEBUG_TAG, "GestureDetector onDown")
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.w(DEBUG_TAG, "GestureDetector onFling")

        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.w(DEBUG_TAG, "GestureDetector onScroll")
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.w(DEBUG_TAG, "GestureDetector onLongPress")
    }

    /* Gesture double tab */
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.w(DEBUG_TAG, "GestureDetector onDoubleTap")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        Log.w(DEBUG_TAG, "GestureDetector onDoubleTapEvent")
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        Log.w(DEBUG_TAG, "GestureDetector onSingleTapConfirmed")
        return true
    }


}
