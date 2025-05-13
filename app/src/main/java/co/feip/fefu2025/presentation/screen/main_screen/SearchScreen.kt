package co.feip.fefu2025.presentation.screen.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.presentation.screen.details.components.AnimeCard
import co.feip.fefu2025.presentation.util.AnimeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModelFactory: SearchViewModel.Factory,
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val viewModel: SearchViewModel = viewModel(factory = viewModelFactory)
    var query by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            },
            actions = {
                Spacer(modifier = Modifier.width(32.dp))

                OutlinedTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        viewModel.updateQuery(it)
                    },
                    placeholder = { Text("Поиск аниме") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    trailingIcon = {
                        if (query.isNotBlank()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Очистить")
                            }
                        }
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (uiState) {
            is AnimeUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            is AnimeUiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text((uiState as AnimeUiState.Error).message)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.retrySearch()
                    }) {
                        Text("Повторить")
                    }

                }
            }


            is AnimeUiState.Success -> {
                val list = (uiState as AnimeUiState.Success).animeList
                if (query.isBlank()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Введите запрос")
                    }
                } else if (list.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Ничего не найдено")
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        items(list) { anime ->
                            AnimeCard(
                                title = anime.title,
                                genres = anime.genres,
                                rating = anime.rating ?: 0f,
                                imageUrl = anime.image,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onAnimeClick(anime.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


