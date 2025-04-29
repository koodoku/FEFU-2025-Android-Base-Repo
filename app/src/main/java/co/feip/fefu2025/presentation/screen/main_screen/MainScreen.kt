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
import co.feip.fefu2025.presentation.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: AnimeListViewModel = viewModel(),
    navController: NavController? = null
) {
    val state = viewModel.animeListState

    when (state) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = state.message)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadAnimeList() }) {
                        Text("Повторить")
                    }
                }
            }
        }

        is UiState.Success -> {
            val animeList = state.data

            Column(modifier = Modifier.fillMaxSize()) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController?.navigate("search") },
                    placeholder = { Text("Поиск аниме") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    },
                    enabled = false
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(animeList) { anime ->
                        AnimeCard(
                            title = anime.title,
                            genres = anime.genres,
                            rating = anime.rating ?: 0f,
                            imageRes = anime.imageRes,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navController?.navigate("details/${anime.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(
            viewModel = AnimeListViewModel(),
            navController = null
        )
    }
}
