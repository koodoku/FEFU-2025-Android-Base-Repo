package co.feip.fefu2025.presentation.screen.recommendations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.screen.details.AnimeDetailsViewModel
import co.feip.fefu2025.presentation.screen.details.components.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(
    animeId: Int,
    navController: NavController,
    viewModel: AnimeDetailsViewModel = viewModel()
) {
    val recommendations = viewModel.state.animeDetails?.similar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Может понравиться") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                recommendations?.let {
                    items(it) { anime ->
                        anime.rating?.let { rating ->
                            AnimeCard(
                                title = anime.title,
                                genres = anime.genres,
                                rating = rating,
                                imageRes = anime.imageRes,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    navController.navigate("details/${anime.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(animeId) {
        viewModel.loadRecommendations(animeId)
    }


}