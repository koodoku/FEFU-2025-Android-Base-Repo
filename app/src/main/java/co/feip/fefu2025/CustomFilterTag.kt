package co.feip.fefu2025

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomFilterTag @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {
    private val textName: TextView
    init {

        LayoutInflater.from(context).inflate(R.layout.genre_filter_item, this, true)
        textName = findViewById(R.id.filterName)
    }
    fun setGenreName(name: String) {
        textName.text = name
    }
    fun setBackgroundRes(color: Int) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.filter_background) as GradientDrawable
        drawable.setColor(color)
        textName.background = drawable
    }
}
