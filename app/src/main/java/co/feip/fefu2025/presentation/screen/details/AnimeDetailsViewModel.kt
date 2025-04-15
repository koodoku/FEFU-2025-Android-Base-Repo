package co.feip.fefu2025.presentation.screen.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.usecase.GetAnimeDetailsUseCase
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {

    private val repository = AnimeRepositoryImpl()
    private val getAnimeDetailsUseCase = GetAnimeDetailsUseCase()

    var state by mutableStateOf(DetailsScreenState())
        private set

    fun loadAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            state = state.copy(
                animeDetails = getAnimeDetailsUseCase(animeId)
            )
        }
    }

    fun loadRecommendations(animeId: Int) {
        val currentAnime = repository.getAnimeById(animeId) ?: return
        val genres = currentAnime.genres ?: return
        val recommendations = repository.getSimilarAnimeByGenres(genres)
            .filter { it.id != animeId }

        state = state.copy(
            animeDetails = currentAnime.copy(similar = recommendations)
        )
    }
}
