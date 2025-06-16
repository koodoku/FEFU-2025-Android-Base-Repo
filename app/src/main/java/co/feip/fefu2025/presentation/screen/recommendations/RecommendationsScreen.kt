package co.feip.fefu2025.presentation.screen.recommendations

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.screen.details.AnimeDetailsViewModel
import co.feip.fefu2025.presentation.screen.details.components.AnimeCard
import co.feip.fefu2025.presentation.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(
    animeId: Int,
    navController: NavController,
    viewModel: RecommendationsViewModel = viewModel()
) {
    val state by viewModel.recommendationsState.collectAsState()

    LaunchedEffect(animeId) {
        viewModel.loadRecommendations(animeId, refresh = true)
    }

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
        when (state) {
            is RecommendationsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is RecommendationsState.Success -> {
                val recommendations = (state as RecommendationsState.Success).recommendations
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recommendations) { anime ->
                        AnimeCard(
                            title = anime.title,
                            genres = anime.genres,
                            rating = anime.rating,
                            imageUrl = anime.image,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navController.navigate("details/${anime.id}")
                            }
                        )
                    }
                    item {
                        LaunchedEffect(Unit) {
                            viewModel.loadRecommendations(animeId)
                        }
                    }
                }
            }
            is RecommendationsState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text((state as RecommendationsState.Error).message)
                }
            }
        }
    }
}
