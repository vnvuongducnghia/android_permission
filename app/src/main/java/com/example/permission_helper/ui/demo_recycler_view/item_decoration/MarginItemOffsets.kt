package com.example.permission_helper.ui.demo_recycler_view.item_decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.permission_helper.ui.demo_recycler_view.RecyclerViewAdapterNormal

class MarginItemOffsets(private val extraMargin: Int ) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val position = parent.getChildAdapterPosition(view)
        if (position + 1 == parent.adapter!!.itemCount) {
            outRect.bottom = extraMargin // unit is px
        }
        if (position % 2 == 0) {
            outRect.right = extraMargin
        } else {
            outRect.left = extraMargin
        }

        val adapter = parent.adapter as RecyclerViewAdapterNormal?
        val item = adapter!!.getItem(position)
        if (item.firstItemInSection) {
            outRect.top = extraMargin
        }
    }
}
