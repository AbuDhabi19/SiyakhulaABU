import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(private val dividerHeight: Int, private val dividerColor: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        color = dividerColor
        strokeWidth = dividerHeight.toFloat()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight

            c.drawLine(child.left.toFloat(), top.toFloat(), child.right.toFloat(), top.toFloat(), paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: android.view.View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = dividerHeight
    }
}
