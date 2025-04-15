package co.feip.fefu2025

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.feip.fefu2025.presentation.navigation.AppNavigation
import co.feip.fefu2025.presentation.navigation.Screen
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FEFU2025AndroidBaseRepoTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}