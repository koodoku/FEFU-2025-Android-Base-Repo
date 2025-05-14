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
        val normalizedQuery = query.trim().lowercase().split("\\s+".toRegex())

        val fullList = getAnimeListUseCase()
        if (fullList.isSuccess) {
            val animeList = fullList.getOrNull() ?: emptyList()
            val filteredList = animeList.filter { anime ->
                normalizedQuery.all { query ->
                    anime.title.lowercase().contains(query)
                }
            }
            _uiState.value = AnimeUiState.Success(filteredList)
        } else {
            _uiState.value =
                AnimeUiState.Error(fullList.exceptionOrNull()?.message ?: "Unknown error")
        }
    }
}


class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(useCase) as T
    }
}
