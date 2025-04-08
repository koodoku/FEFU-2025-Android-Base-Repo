package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import co.feip.fefu2025.presentation.screen.details.AnimeScreen
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme

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