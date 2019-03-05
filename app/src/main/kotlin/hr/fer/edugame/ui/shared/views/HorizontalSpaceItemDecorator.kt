package hr.fer.edugame.ui.shared.views

import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

class HorizontalSpaceItemDecorator(
    @DimenRes private val spaceSizeRes: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = view.context.resources.getDimensionPixelOffset(spaceSizeRes)
    }
}