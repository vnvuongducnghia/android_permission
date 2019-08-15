package com.example.permission_helper.ui.demo_recycler_view.item_decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView
import android.view.View

class CanvasDrawLine(@ColorInt color: Int, width: Float) : RecyclerView.ItemDecoration() {
    private val mBrush: Paint = Paint()
    private val mAlpha: Int

    init {
        mBrush.color = color
        mBrush.strokeWidth = width
        mAlpha = mBrush.alpha
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val params = view.layoutParams as RecyclerView.LayoutParams
        val position = params.viewAdapterPosition
        if (position < state.itemCount) {
            outRect.set(0, 0, 0, mBrush.strokeWidth.toInt())
        } else {
            outRect.setEmpty()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val offset = (mBrush.strokeWidth / 2).toInt() // position at center offset
        for (i in 0 until parent.childCount) {
            val childView = parent.getChildAt(i)
            val childParams = childView.layoutParams as RecyclerView.LayoutParams
            val childPosition = childParams.viewAdapterPosition
            if (childPosition < state.itemCount) {
                mBrush.alpha = (childView.alpha * mAlpha).toInt()
                val startX = childView.left.toFloat() + childView.translationX // + value move
                val startY = childView.bottom.toFloat() + offset.toFloat() + childView.translationY
                val stopX = childView.right + childView.translationX
                val stopY = childView.bottom.toFloat() + offset.toFloat() + childView.translationY
                c.drawLine(startX, startY, stopX, stopY, mBrush)
            }
        }
    }
}

