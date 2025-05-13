package co.feip.fefu2025.presentation.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.screen.details.components.AnimeCard
import co.feip.fefu2025.presentation.screen.details.components.RatingChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    navController: NavController? = null,
    viewModel: AnimeDetailsViewModel = viewModel()
) {
    val animeDetailsState = viewModel.animeDetailsState

    LaunchedEffect(animeId) {
        viewModel.loadAnimeDetails(animeId)
    }

//    when (animeDetailsState) {
//        is UiState.Loading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        }
//
//        is UiState.Error -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(
//                        text = animeDetailsState.message,
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    androidx.compose.material3.Button(onClick = { viewModel.retry() }) {
//                        Text("Повторить")
//                    }
//                }
//            }
//        }
//
//        is UiState.Success -> {
//            val details = animeDetailsState.data
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                if (navController != null) {
//                    IconButton(
//                        onClick = { navController.popBackStack() },
//                        modifier = Modifier.align(Alignment.Start)
//                    ) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(8.dp)
//                ) {
//                    Row(
//                        modifier = Modifier.padding(12.dp),
//                        verticalAlignment = Alignment.Top
//                    ) {
//                        details.imageRes?.let {
//                            Image(
//                                painter = painterResource(id = it),
//                                contentDescription = details.title,
//                                modifier = Modifier
//                                    .width(150.dp)
//                                    .aspectRatio(2f / 3f)
//                                    .clip(RoundedCornerShape(8.dp)),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(16.dp))
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text(
//                                details.title,
//                                style = MaterialTheme.typography.headlineMedium,
//                                fontWeight = FontWeight.Bold
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text("Рейтинг: ${details.rating}", style = MaterialTheme.typography.bodyLarge)
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text("Год: ${details.year}", style = MaterialTheme.typography.bodyLarge)
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text("Эпизодов: ${details.episodes}", style = MaterialTheme.typography.bodyLarge)
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    "Жанры: ${details.genres?.joinToString(", ")}",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text("Описание:", style = MaterialTheme.typography.titleMedium)
//                Text(details.description ?: "", style = MaterialTheme.typography.bodyLarge)
//
//                Spacer(modifier = Modifier.height(16.dp))
//                details.rating?.let { rating ->
//                    RatingChart(ratings = mapOf(
//                        1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 300,
//                        6 to 400, 7 to 600, 8 to 800, 9 to 700, 10 to 500
//                    ))
//                }
//
//                Spacer(modifier = Modifier.height(24.dp))
//                Text(
//                    text = "Может понравиться",
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier
//                        .clickable {
//                            navController?.navigate("recommendations/${details.id}")
//                        }
//                        .padding(vertical = 8.dp)
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                details.similar?.let { animeList ->
//                    LazyRow(
//                        horizontalArrangement = Arrangement.spacedBy(16.dp),
//                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
//                    ) {
//                        items(animeList) { recommendedAnime ->
//                            recommendedAnime.rating?.let {
//                                AnimeCard(
//                                    title = recommendedAnime.title,
//                                    genres = recommendedAnime.genres,
//                                    rating = it,
//                                    imageUrl = recommendedAnime.image,
//                                    modifier = Modifier.width(160.dp),
//                                    onClick = {
//                                        navController?.navigate("details/${recommendedAnime.id}")
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}


@Preview
@Composable
fun AnimeScreenPreview() {
    AnimeDetailsScreen(
        animeId = 1,
        navController = null
    )
}