package co.feip.fefu2025.presentation.screen.details

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.usecase.GetAnimeDetailsUseCase
import co.feip.fefu2025.presentation.util.UiState
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {

    private val repository = AnimeRepositoryImpl()
    private val getAnimeDetailsUseCase = GetAnimeDetailsUseCase(repository)


    var animeDetailsState by mutableStateOf<UiState<AnimeDetails>>(UiState.Loading)
        private set

    private var lastLoadedAnimeId: Int? = null

    fun loadAnimeDetails(animeId: Int) {
        lastLoadedAnimeId = animeId
        animeDetailsState = UiState.Loading
        viewModelScope.launch {
            try {
                val animeDetails = getAnimeDetailsUseCase(animeId)
                val genres = animeDetails?.genres ?: emptyList()
                val recommendations = repository.getSimilarAnimeByGenres(genres)
                    .filter { it.id != animeId }

                val fullAnime = animeDetails?.copy(similar = recommendations)
                animeDetailsState = UiState.Success(fullAnime) as UiState<AnimeDetails>
            } catch (e: Exception) {
                animeDetailsState = UiState.Error("Ошибка загрузки аниме")
            }
        }
    }

    fun retry() {
        lastLoadedAnimeId?.let { loadAnimeDetails(it) }
    }
}
