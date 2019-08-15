package com.example.permission_helper.ui.demo_recycler_view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/*An OnItemTouchListener functions a bit differently than the normal OnItemClickListener. Using the OnItemTouchListener, it is possible to allow the application to intercept touch events from the View hierarchy. What this basically means is that you can implement various forms of gesture manipulation like swipe straight into the Views of your RecyclerView.*/
/*If you want to implement an OnItemTouchListener into your RecyclerView, you will need to determine the MotionEvent that you're going to use.*/

class RecyclerTouchListener(
    context: Context, recyclerView: RecyclerView,
    val onClickListener: RecyclerTouchListener.OnClickListener?
) : RecyclerView.OnItemTouchListener {


    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                if (e != null) {
                    val view = recyclerView.findChildViewUnder(e.x, e.y)
                    if (view != null && onClickListener != null) {
                        onClickListener.onItemLongClick(view, recyclerView.getChildPosition(view))
                    }
                }
            }
        })
    }

    override fun onTouchEvent(p0: RecyclerView, p1: MotionEvent) {}

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val view = rv.findChildViewUnder(e.x, e.y)
        if (view != null && onClickListener != null && gestureDetector.onTouchEvent(e)) {
            onClickListener.onItemClick(view, rv.getChildPosition(view))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {}

    /**
     * On click listener
     */
    interface OnClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int) {}
    }
}