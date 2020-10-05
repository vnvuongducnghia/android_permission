package com.example.permission_helper.ui.demo_recycler_view.item_decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildLayoutPosition(view)
        val manager = parent.layoutManager as GridLayoutManager?

        outRect.left = mItemOffset

        if (position % 2 != 0) {
            outRect.right = mItemOffset
        }

        if (position < manager!!.spanCount) {
            outRect.top = mItemOffset
        }
        outRect.bottom = mItemOffset
    }
}
