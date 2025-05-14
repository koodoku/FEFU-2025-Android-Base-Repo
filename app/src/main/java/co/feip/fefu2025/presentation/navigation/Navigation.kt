package co.feip.fefu2025.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.feip.fefu2025.domain.usecase.AnimeSearchUseCase
import co.feip.fefu2025.domain.usecase.GetAnimeDetailsUseCase
import co.feip.fefu2025.presentation.screen.details.AnimeDetailsScreen
import co.feip.fefu2025.presentation.screen.details.AnimeDetailsViewModel
import co.feip.fefu2025.presentation.screen.details.AnimeDetailsViewModelFactory
import co.feip.fefu2025.presentation.screen.main_screen.AnimeListViewModel
import co.feip.fefu2025.presentation.screen.main_screen.MainScreen
import co.feip.fefu2025.presentation.screen.details.search.SearchScreen
import co.feip.fefu2025.presentation.screen.details.search.SearchScreenViewModel
import co.feip.fefu2025.presentation.screen.details.search.SearchScreenViewModelFactory
import co.feip.fefu2025.presentation.screen.main_screen.SearchViewModel
import co.feip.fefu2025.presentation.screen.recommendations.RecommendationsScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavigation(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            val viewModel = viewModel<AnimeListViewModel>()
            val state = viewModel.animeListState
            MainScreen(
                state = state,
                navigateToDetails = { id ->
                    navController.navigate(Screen.detailsRoute(id))
                },
                navigateToSearch = {
                    navController.navigate("search")
                },
                loadAnimeList = viewModel::loadAnimeList,
            )
        }

        composable("search") {
            val searchUseCase = AnimeSearchUseCase()
            val factory = SearchScreenViewModelFactory(searchUseCase)
            val viewModel = ViewModelProvider(it, factory)[SearchScreenViewModel::class.java]
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate("details/$id") },
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
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
            val getAnimeDetailsUseCase = GetAnimeDetailsUseCase()
            val factory =  AnimeDetailsViewModelFactory(animeId, getAnimeDetailsUseCase)
            val viewModel = ViewModelProvider(backStackEntry, factory)[AnimeDetailsViewModel::class.java]

            AnimeDetailsScreen(
                animeId = animeId,
                navController = navController,
                viewModel = viewModel,
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
            val parentEntry = remember { navController.getBackStackEntry("${Screen.Details.route}/$animeId") }
            val sharedViewModel = ViewModelProvider(parentEntry)[AnimeDetailsViewModel::class.java]
            RecommendationsScreen(
                animeId = animeId,
                navController = navController,
                viewModel = sharedViewModel,
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Details : Screen("details")
    object Recommendations : Screen("recommendations")

    companion object {
        fun detailsRoute(animeId: Int) = "${Details.route}/$animeId"
        fun recommendationsRoute(animeId: Int) = "${Recommendations.route}/$animeId"
    }
}