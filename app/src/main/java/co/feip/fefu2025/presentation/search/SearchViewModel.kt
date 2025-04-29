package co.feip.fefu2025.presentation.screen.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.presentation.util.AnimeUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeUiState>(AnimeUiState.Success(emptyList()))
    val uiState: StateFlow<AnimeUiState> = _uiState

    private val queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(200)
                .distinctUntilChanged()
                .collectLatest { query ->
                    performSearch(query)
                }
        }
    }
    fun retrySearch() {
        viewModelScope.launch {
            performSearch(queryFlow.value)
        }
    }


    fun updateQuery(newQuery: String) {
        queryFlow.value = newQuery
    }

    private suspend fun performSearch(query: String) {
        if (query.isBlank()) {
            _uiState.value = AnimeUiState.Success(emptyList())
            return
        }

        _uiState.value = AnimeUiState.Loading
        try {
            val normalizedQuery = query.trim().lowercase().split("\\s+".toRegex())

            val fullList = getAnimeListUseCase()
            val filtered = fullList.filter { anime ->
                val normalizedTitle = anime.title.trim().lowercase()
                normalizedQuery.all { token ->
                    normalizedTitle.contains(token)
                }
            }

            _uiState.value = AnimeUiState.Success(filtered)
        } catch (e: Exception) {
            _uiState.value = AnimeUiState.Error("Не удалось загрузить данные")
        }
    }


    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(useCase) as T
        }
    }
}
