package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.presentation.navigation.AppNavigation
import co.feip.fefu2025.presentation.screen.main_screen.Factory
import co.feip.fefu2025.presentation.screen.main_screen.SearchViewModel
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FEFU2025AndroidBaseRepoTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                )
            }
        }
    }
}