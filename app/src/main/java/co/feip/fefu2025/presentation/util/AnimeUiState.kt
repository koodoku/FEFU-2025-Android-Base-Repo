package co.feip.fefu2025.presentation.util

import co.feip.fefu2025.domain.model.Anime

sealed class AnimeUiState {
    object Loading : AnimeUiState()
    data class Success(val animeList: List<Anime>) : AnimeUiState()
    data class Error(val message: String) : AnimeUiState()
}