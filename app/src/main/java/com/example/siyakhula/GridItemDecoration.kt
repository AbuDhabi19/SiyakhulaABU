package com.example.siyakhula

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        color = android.graphics.Color.LTGRAY // Set the color of the lines
        strokeWidth = 7f // Set the thickness of the lines
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            // Draw horizontal lines
            c.drawLine(
                child.left.toFloat(),
                child.bottom + params.bottomMargin.toFloat(),
                child.right.toFloat(),
                child.bottom + params.bottomMargin.toFloat(),
                paint
            )

            // Draw vertical lines
            if ((i + 1) % 2 == 0) { // Adjust based on number of columns (2 for grid with 2 columns)
                val nextChild = parent.getChildAt(i + 1)
                if (nextChild != null) {
                    c.drawLine(
                        child.right + params.rightMargin.toFloat(),
                        child.top.toFloat(),
                        child.right + params.rightMargin.toFloat(),
                        child.bottom.toFloat(),
                        paint
                    )
                }
            }
        }
    }
}
