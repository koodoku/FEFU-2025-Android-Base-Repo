package co.feip.fefu2025.presentation.screen.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.screen.details.components.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    navigateToDetails: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    loadAnimeList: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Аниме") },
                actions = {
                    IconButton(onClick = navigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading && state.animeList.isEmpty() -> {
                    // Показываем скелетон загрузки только при первой загрузке
                    LoadingSkeleton()
                }
                state.error != null && state.animeList.isEmpty() -> {
                    // Показываем ошибку только если нет данных
                    ErrorContent(
                        error = state.error,
                        onRetry = loadAnimeList
                    )
                }
                else -> {
                    // Показываем список аниме
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.animeList) { anime ->
                            AnimeCard(
                                title = anime.title,
                                genres = anime.genres,
                                rating = anime.rating,
                                imageUrl = anime.image,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { navigateToDetails(anime.id) }
                            )
                        }
                    }
                    
                    // Показываем индикатор загрузки поверх списка при обновлении
                    if (state.isLoading && state.animeList.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingSkeleton() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(6) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}

//@Preview
//@Composable
//fun MainScreenPreview() {
//    MaterialTheme {
//        MainScreen(
//            viewModel = AnimeListViewModel(),
//            navController = null
//        )
//    }
//}
