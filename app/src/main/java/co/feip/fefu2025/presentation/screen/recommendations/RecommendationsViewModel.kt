package co.feip.fefu2025.presentation.screen.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendationsViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _recommendationsState = MutableStateFlow<RecommendationsState>(RecommendationsState.Loading)
    val recommendationsState: StateFlow<RecommendationsState> = _recommendationsState.asStateFlow()

    private var currentPage = 1
    private var isLoading = false
    private var hasNextPage = true

    fun loadRecommendations(animeId: Int, refresh: Boolean = false) {
        if (refresh) {
            currentPage = 1
            hasNextPage = true
        }

        if (isLoading || !hasNextPage) return

        viewModelScope.launch {
            try {
                isLoading = true
                if (currentPage == 1) {
                    _recommendationsState.value = RecommendationsState.Loading
                }

                val recommendations = repository.getAnimeRecommendations(animeId, currentPage)
                
                if (recommendations.isEmpty()) {
                    hasNextPage = false
                } else {
                    currentPage++
                }

                val currentRecommendations = when (val state = _recommendationsState.value) {
                    is RecommendationsState.Success -> if (refresh) recommendations else state.recommendations + recommendations
                    else -> recommendations
                }

                _recommendationsState.value = RecommendationsState.Success(currentRecommendations)
            } catch (e: Exception) {
                _recommendationsState.value = RecommendationsState.Error(e.message ?: "Неизвестная ошибка")
            } finally {
                isLoading = false
            }
        }
    }
}

sealed class RecommendationsState {
    data object Loading : RecommendationsState()
    data class Success(val recommendations: List<Anime>) : RecommendationsState()
    data class Error(val message: String) : RecommendationsState()
} 