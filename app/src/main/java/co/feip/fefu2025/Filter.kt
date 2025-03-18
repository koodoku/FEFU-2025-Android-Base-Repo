package co.feip.fefu2025

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class Filter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val tvLanguage: TextView
    private val tvPercent: TextView
    private val viewDot: View

    init {
        LayoutInflater.from(context).inflate(R.layout.language_filter_item, this, true)
        tvLanguage = findViewById(R.id.tvLanguage)
        tvPercent = findViewById(R.id.tvPercent)
        viewDot = findViewById(R.id.viewDot)
    }

    fun setItem(name: String, color: Int, percentage: Float) {
        tvLanguage.text = name
        tvPercent.text = "${percentage.toInt()}%"
        val drawable = ContextCompat.getDrawable(context, R.drawable.circle) as GradientDrawable
        drawable.setColor(color)
        viewDot.background = drawable
    }
}