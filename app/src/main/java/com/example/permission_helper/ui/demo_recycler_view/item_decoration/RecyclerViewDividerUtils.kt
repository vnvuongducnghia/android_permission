package com.example.permission_helper.ui.demo_recycler_view.item_decoration

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.*
import com.example.testrecyclerviewdt.util.ScreenUtils


object RecyclerViewDividerUtils {

    fun dividerMiddle(context: Context): RecyclerView.ItemDecoration = DividerItemDecorationNoLast(context, DividerItemDecoration.VERTICAL)
    fun divider(context: Context): RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

    fun divider(context: Context, height: Int): RecyclerView.ItemDecoration {
        val itemDecoration = DividerItemDecorationNoLast(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawableBySize(height))
        return itemDecoration
    }

    fun divider(context: Context, height: Int, color: Int): RecyclerView.ItemDecoration {
        val itemDecoration = DividerItemDecorationNoLast(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawableBySize(height, color))
        return itemDecoration
    }

    private fun getDrawableBySize(size: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(Color.parseColor("#c4c8ce"))
        drawable.setSize(size, size)
        return drawable
    }

    private fun getDrawableBySize(size: Int, color: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(color)
        drawable.setSize(size, size)
        return drawable
    }

    fun horizontalSpaceDivider(): RecyclerView.ItemDecoration {
        return horizontalSpaceDivider(ScreenUtils.dpToPx(10f))
    }

    fun horizontalSpaceDivider(height: Int): RecyclerView.ItemDecoration {
        return HorizontalSpacingItemDecoration(height)
    }

    fun horizontalSpaceDivider(value: Float): RecyclerView.ItemDecoration {
        return horizontalSpaceDivider(ScreenUtils.dpToPx(value))
    }

    fun gridDivider(): RecyclerView.ItemDecoration {
        return SpacingDecoration(ScreenUtils.dpToPx(5f), ScreenUtils.dpToPx(20f), false)
    }

    fun gridBaseDivider(): RecyclerView.ItemDecoration {
        return GridDecoration(ScreenUtils.dpToPx(5f), ScreenUtils.dpToPx(20f), ScreenUtils.dpToPx(10f))
    }

    fun gridDivider(horizontallSpacing: Float, verticalSpacing: Float): RecyclerView.ItemDecoration {
        return SpacingDecoration(ScreenUtils.dpToPx(horizontallSpacing), ScreenUtils.dpToPx(verticalSpacing), false)
    }

    class DividerItemDecorationNoLast(context: Context?, orientation: Int) : DividerItemDecoration(context, orientation) {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
                // hide the divider for the last child
                outRect.setEmpty()
            } else {
                super.getItemOffsets(outRect, view, parent, state)
            }
        }
    }

    class HorizontalSpacingItemDecoration :
        RecyclerView.ItemDecoration {

        private var spacing: Int = 0
        private var includeEdge: Boolean = false
        private var headerNum: Int = 0

        constructor(spacing: Int) {
            this.spacing = spacing
            this.includeEdge = true
        }

        constructor(spacing: Int, includeEdge: Boolean, headerNum: Int) {
            this.spacing = spacing
            this.includeEdge = includeEdge
            this.headerNum = headerNum
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) - headerNum // item position
            val size = parent.adapter!!.itemCount

            outRect.left = 0
            outRect.right = 0
            outRect.top = 0
            outRect.bottom = 0
            if (position >= 0) {
                outRect.top = 0
                outRect.bottom = 0
                if (position == 0) { // left edge
                    outRect.left = if (includeEdge) spacing else 0
                } else {
                    outRect.left = spacing
                }
                if (position >= size - 1) { // right edge
                    outRect.right = if (includeEdge) spacing else 0
                } else {
                    outRect.right = 0
                }
            }
        }
    }

    class SpacingDecoration(hSpacing: Int, vSpacing: Int, includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        private var mHorizontalSpacing = 0
        private var mVerticalSpacing = 0
        private var mIncludeEdge = false

        init {
            mHorizontalSpacing = hSpacing
            mVerticalSpacing = vSpacing
            mIncludeEdge = includeEdge
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            // Only handle the vertical situation
            val position = parent.getChildAdapterPosition(view)
            if (parent.layoutManager is GridLayoutManager) {
                val layoutManager = parent.layoutManager as GridLayoutManager
                val spanCount = layoutManager.spanCount
                val column = position % spanCount
                getGridItemOffsets(outRect, position, column, spanCount)
            } else if (parent.layoutManager is StaggeredGridLayoutManager) {
                val layoutManager = parent.layoutManager as StaggeredGridLayoutManager
                val spanCount = layoutManager.spanCount
                val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                val column = lp.spanIndex
                getGridItemOffsets(outRect, position, column, spanCount)
            } else if (parent.layoutManager is LinearLayoutManager) {
                outRect.left = mHorizontalSpacing
                outRect.right = mHorizontalSpacing
                if (mIncludeEdge) {
                    if (position == 0) {
                        outRect.top = mVerticalSpacing
                    }
                    outRect.bottom = mVerticalSpacing
                } else {
                    if (position > 0) {
                        outRect.top = mVerticalSpacing
                    }
                }
            }
        }

        private fun getGridItemOffsets(outRect: Rect, position: Int, column: Int, spanCount: Int) {
            if (mIncludeEdge) {
                outRect.left = mHorizontalSpacing * (spanCount - column) / spanCount
                outRect.right = mHorizontalSpacing * (column + 1) / spanCount
                if (position < spanCount) {
                    outRect.top = mVerticalSpacing
                }
                outRect.bottom = mVerticalSpacing
            } else {
                outRect.left = mHorizontalSpacing * column / spanCount
                outRect.right = mHorizontalSpacing * (spanCount - 1 - column) / spanCount
                if (position >= spanCount) {
                    outRect.top = mVerticalSpacing
                }
            }
        }
    }

    class GridDecoration(private val horizontalSpacing: Int, private val verticalSpacing: Int, private val padding: Int) : RecyclerView.ItemDecoration() {

        private var mPaint: Paint = Paint()

        init {
            mPaint.style = Paint.Style.FILL
            mPaint.color = Color.BLUE
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent.getChildAdapterPosition(view)
            val viewType = parent.adapter?.getItemViewType(position)
            if (parent.layoutManager is GridLayoutManager) {
                val layoutManager = parent.layoutManager as GridLayoutManager
                val spanCount = layoutManager.spanCount
                when (viewType) {
                    // BaseHFAdapter.TYPE_HEADER -> outRect.top = padding
                    // BaseHFAdapter.TYPE_FOOTER -> outRect.bottom = padding
                    else -> {
                        val column = position % spanCount
                        outRect.left = horizontalSpacing
                        outRect.right = 0
                        outRect.top = verticalSpacing
                        if (column == 1) {
                            outRect.left = padding
                        } else if (column == 0) {
                            outRect.right = padding
                        }
                    }
                }
            }
        }
    }
}