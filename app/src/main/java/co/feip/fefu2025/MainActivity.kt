package co.feip.fefu2025

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import co.feip.fefu2025.Consts.genres
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.addNewFilter)
        button.setOnClickListener {
            addNewFilter()
        }
    }

    private fun addNewFilter() {
        val customLayout = findViewById<CustomLayout>(R.id.customLayout)
        val randomNewFilter = Filter(this).apply {
            setItem(
                name = genres.random(),
                color = android.graphics.Color.argb(255,
                    Random.nextInt(256),
                    Random.nextInt(256),
                    Random.nextInt(256)
                ),
                percentage = Random.nextFloat() * 100
            )
        }
        customLayout.addView(randomNewFilter)
    }
}