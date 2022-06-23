package com.sky.lux.widget.dialog

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sky.lux.utils.dp2px

class GridItemDecoration : RecyclerView.ItemDecoration() {

    var marginLeftRight = 16.dp2px
    var marginTopBottom = 4.dp2px
    var marginMiddle = 8.dp2px

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewHolder = parent.findContainingViewHolder(view) ?: return
        val position = viewHolder.absoluteAdapterPosition

        handleLiveItemDecoration(outRect, parent, position)
        outRect.top = marginTopBottom
        outRect.bottom = marginTopBottom
    }

    private fun handleLiveItemDecoration(outRect: Rect, parent: RecyclerView, position: Int) {
        if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager?
            val spanSizeLookup = layoutManager?.spanSizeLookup
            val spanCount = layoutManager?.spanCount ?: 0
            val spanSize = spanSizeLookup?.getSpanSize(position)
            val spanIndex = spanSizeLookup?.getSpanIndex(position, spanCount) ?: 0
            if (spanSize != spanCount) {
                if (spanIndex % 2 == 0) {
                    //左边item
                    outRect.left = marginLeftRight
                    outRect.right = marginMiddle / 2
                } else {
                    //右边item
                    outRect.left = marginMiddle / 2
                    outRect.right = marginLeftRight
                }
            }
        }
    }
}