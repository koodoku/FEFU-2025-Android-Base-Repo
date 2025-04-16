package co.feip.fefu2025.anime_ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val animeList = listOf(
        AnimeCardData("Ghost in the Shell", listOf("Киберпанк", "Экшен", "Детектив"), 8.3f, R.drawable.ghost_in_the_shell),
        AnimeCardData("Call of the Night", listOf("Романтика", "Сверхъестественное"), 8.1f, R.drawable.call_of_the_night),
        AnimeCardData("Nier Automata Ver 1.1", listOf("Фантастика", "Экшен", "Драма"), 8.5f, R.drawable.nier),
        AnimeCardData("Bocchi the Rock!", listOf("Комедия", "Музыка", "Повседневность"), 8.8f, R.drawable.boochi),
        AnimeCardData("Кобаяши", listOf("Комедия", "Фэнтези", "Сэйнэн"), 8.4f, R.drawable.kobayashi),
        AnimeCardData("Мелочи жизни", listOf("Комедия", "Повседневность"), 8.6f, R.drawable.nichijou),
        AnimeCardData("Фарфоровая кукла", listOf("Драма", "Романтика", "Повседневность"), 8.3f, R.drawable.farfor),
        AnimeCardData("Хоримия", listOf("Романтика", "Комедия", "Школа"), 8.7f, R.drawable.horimiya),
        AnimeCardData("Моя подруга олениха Нокотан", listOf("Комедия", "Повседневность"), 8.2f, R.drawable.shikanoko),
        AnimeCardData("Реинкарнация безработного", listOf("Исекай", "Комедия", "Драма"), 8.0f, R.drawable.reink)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Поиск аниме") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Поиск") }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(animeList) { anime ->
                AnimeCard(
                    title = anime.title,
                    genres = anime.genres,
                    rating = anime.rating,
                    imageRes = anime.imageRes,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


data class AnimeCardData(
    val title: String,
    val genres: List<String>,
    val rating: Float,
    val imageRes: Int
)

@Composable
@Preview
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}