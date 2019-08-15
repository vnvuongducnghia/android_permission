package com.example.permission_helper.ui.demo_recycler_view.item_decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.permission_helper.R

class DrawableDrawLine(context: Context) : RecyclerView.ItemDecoration() {
    private val mBrush: Drawable = context.resources.getDrawable(R.drawable.divider_blue)
    private val mAlpha: Int = mBrush.alpha

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val param = view.layoutParams as RecyclerView.LayoutParams
        val position = param.viewAdapterPosition
        if (position < state.itemCount) {
            outRect.set(0, 0, 0, mBrush.intrinsicHeight)
        } else {
            outRect.setEmpty()
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val childParams = childView.layoutParams as RecyclerView.LayoutParams
            val childPosition = childParams.viewAdapterPosition
            if (childPosition < state.itemCount) {
                val left = childView.left
                val right = childView.right
                val top = childView.bottom + childParams.bottomMargin
                val bottom = top + mBrush.intrinsicHeight
                mBrush.alpha = (childView.alpha * mAlpha).toInt()
                mBrush.setBounds(left, top, right, bottom)
                mBrush.draw(c)
            }
        }
    }
}
