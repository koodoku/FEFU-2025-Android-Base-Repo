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


    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    else if (state.error != null ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Ошибка: ${state.error}")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { loadAnimeList() }) {
                    Text("Повторить")
                }
            }
        }
    }

    else {
        val animeList = state.animeList

        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navigateToSearch() },
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
                        imageUrl = anime.image,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navigateToDetails(anime.id) }
                    )
                }
            }
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
