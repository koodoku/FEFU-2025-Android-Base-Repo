package co.feip.fefu2025

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.feip.fefu2025.presentation.navigation.AppNavigation
import co.feip.fefu2025.presentation.navigation.Screen
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme

class MainActivity : AppCompatActivity() {
    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FEFU2025AndroidBaseRepoTheme {
                navController = rememberNavController()
                navController?.let { controller ->
                    AppNavigation(navController = controller)
                }
            }
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            val uri = intent.data
            if (uri != null) {
                handleDeepLink(uri)
            }
        }
    }

    private fun handleDeepLink(uri: Uri) {
        if (uri.scheme == "mysuperapp" && uri.host == "anime") {
            val pathSegments = uri.pathSegments
            if (pathSegments.isNotEmpty()) {
                val animeId = pathSegments[0].toIntOrNull()
                if (animeId != null) {
                    navController?.navigate(Screen.detailsRoute(animeId)) {
                        popUpTo(Screen.Main.route) {
                            inclusive = false
                        }
                    }
                }
            }
        }
    }
}