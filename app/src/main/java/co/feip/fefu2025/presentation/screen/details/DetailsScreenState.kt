package co.feip.fefu2025.presentation.screen.details

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails

data class DetailsScreenState(
    val isLoading: Boolean = false,
    val animeDetails: AnimeDetails? = null,
    val searchQuery: String= ""
)
