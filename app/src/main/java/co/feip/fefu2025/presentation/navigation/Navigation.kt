package co.feip.fefu2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.feip.fefu2025.presentation.screen.details.AnimeDetailsScreen
import co.feip.fefu2025.presentation.screen.main_screen.MainScreen
import co.feip.fefu2025.presentation.screen.recommendations.RecommendationsScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }

        composable(
            route = "${Screen.Details.route}/{animeId}",
            arguments = listOf(
                navArgument("animeId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            AnimeDetailsScreen(
                animeId = animeId,
                navController = navController
            )
        }

        composable(
            route = "${Screen.Recommendations.route}/{animeId}",
            arguments = listOf(
                navArgument("animeId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            RecommendationsScreen(
                animeId = animeId,
                navController = navController
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Details : Screen("details")
    object Recommendations : Screen("recommendations")

    companion object {
        // Функции для удобного создания маршрутов
        fun detailsRoute(animeId: Int) = "${Details.route}/$animeId"
        fun recommendationsRoute(animeId: Int) = "${Recommendations.route}/$animeId"
    }
}