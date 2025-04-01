package co.feip.fefu2025

import android.os.Bundle
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import co.feip.fefu2025.anime_ui.AnimeScreen
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FEFU2025AndroidBaseRepoTheme {
                AnimeScreen()
            }
        }
    }

}