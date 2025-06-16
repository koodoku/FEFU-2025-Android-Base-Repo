package co.feip.fefu2025.presentation.screen.details.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.presentation.screen.details.components.AnimeCard
import co.feip.fefu2025.presentation.screen.main_screen.SearchViewModel
import co.feip.fefu2025.presentation.util.AnimeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchScreenState,
    onEvent: (SearchScreenEvent) -> Unit,
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
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
                    value = state.query,
                    onValueChange = {
                        onEvent(
                            SearchScreenEvent.onSearch(it)
                        )
                    },
                    placeholder = { Text("Поиск аниме") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    trailingIcon = {
                        if (state.query.isNotBlank()) {
                            IconButton(onClick = { onEvent(SearchScreenEvent.clearQuery) }) {
                                Icon(Icons.Default.Clear, contentDescription = "Очистить")
                            }
                        }
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isInitialLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        onEvent(SearchScreenEvent.onSearch(state.query))
                    }) {
                        Text("Повторить")
                    }
                }
            }
        } else {
            val list = state.animeList
            if (state.query.isBlank()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Введите запрос")
                }
            } else if (list.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ничего не найдено")
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
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
                        
                        if (state.hasNextPage) {
                            item(span = { GridItemSpan(2) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (state.isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        Button(
                                            onClick = { onEvent(SearchScreenEvent.loadNextPage) }
                                        ) {
                                            Text("Загрузить еще")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


