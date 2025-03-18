package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class CustomLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var lineHeight = 0
        val containerWidth = MeasureSpec.getSize(widthMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight


            if (widthUsed + childWidth > containerWidth) {
                widthUsed = 0
                heightUsed += lineHeight
                lineHeight = childHeight
            } else {
                lineHeight = maxOf(lineHeight, childHeight)
            }

            widthUsed += childWidth
        }
        val totalHeight = heightUsed + lineHeight
        setMeasuredDimension(containerWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var currentX = 0
        var currentY = 0
        var lineHeight = 0

        val containerWidth = right - left

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (currentX + childWidth > containerWidth) {
                currentX = left
                currentY += lineHeight
                lineHeight = 0
            }
            child.layout(currentX, currentY, currentX + childWidth, currentY + childHeight)
            currentX += childWidth
            lineHeight = maxOf(lineHeight, childHeight)
        }
    }
}