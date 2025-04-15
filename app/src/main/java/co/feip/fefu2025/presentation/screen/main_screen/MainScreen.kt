package co.feip.fefu2025.presentation.screen.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.screen.details.components.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: AnimeListViewModel = viewModel(),
    navController: NavController? = null
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        // Поисковая строка
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {query->
                viewModel.onQueryChange(query)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { "Поиск аниме" },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Поиск"
                )
            }
        )

        // Список аниме
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.animeList) { anime ->
                anime.rating?.let {
                    AnimeCard(
                        title = anime.title,
                        genres = anime.genres,
                        rating = it,
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

@Composable
@Preview
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(
            viewModel = AnimeListViewModel(),
            navController = null
        )
    }
}