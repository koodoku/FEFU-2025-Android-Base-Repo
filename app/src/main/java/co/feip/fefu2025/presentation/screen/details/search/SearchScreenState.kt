package co.feip.fefu2025.presentation.screen.details.search

import co.feip.fefu2025.domain.model.Anime

data class SearchScreenState(
    val query: String = "",
    val isLoading: Boolean = false,
    val animeList: List<Anime> = emptyList(),
    val error: String? = null,
)
