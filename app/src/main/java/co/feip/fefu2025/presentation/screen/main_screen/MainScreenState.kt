package co.feip.fefu2025.presentation.screen.main_screen

import co.feip.fefu2025.domain.model.Anime

data class MainScreenState(
    val isLoading: Boolean = false,
    val animeList: List<Anime> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null,
    val currentPage: Int = 1,
    val hasNextPage: Boolean = true
)
