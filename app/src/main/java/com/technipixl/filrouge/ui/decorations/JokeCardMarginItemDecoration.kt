package com.technipixl.filrouge.ui.decorations

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.filrouge.R

class JokeCardMarginItemDecoration(
    private val spaceSize: Int,
    private val totalCount: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = 0
            right = 0
            when {
                parent.getChildAdapterPosition(view) == 0 -> {
                    top = spaceSize * 3
                    bottom = spaceSize / 2
                }
                parent.getChildAdapterPosition(view) == totalCount - 1 -> {
                    top = spaceSize / 2
                    bottom = spaceSize
                }
                else -> {
                    top = spaceSize / 2
                    bottom = spaceSize / 2
                }
            }
        }
    }
}