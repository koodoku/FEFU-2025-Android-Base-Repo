package co.feip.fefu2025.presentation.screen.details.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.usecase.AnimeSearchUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class SearchScreenViewModel(val searchUseCase: AnimeSearchUseCase): ViewModel() {
    var state by mutableStateOf(SearchScreenState())
        private set

    private var searchJob: Job? = null
    private val searchQueryFlow = MutableStateFlow("")
    private val SEARCH_TIMEOUT = 10000L // 10 секунд таймаут

    init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(500) // 500ms debounce
                .distinctUntilChanged()
                .collect { query ->
                    performSearch(query, isInitial = true)
                }
        }
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.clearQuery -> {
                searchJob?.cancel()
                state = state.copy(
                    query = "",
                    animeList = emptyList(),
                    currentPage = 1,
                    hasNextPage = true,
                    error = null
                )
                searchQueryFlow.value = ""
            }
            is SearchScreenEvent.onSearch -> {
                searchJob?.cancel()
                state = state.copy(
                    query = event.query,
                    error = null
                )
                searchQueryFlow.value = event.query
            }
            is SearchScreenEvent.loadNextPage -> {
                if (!state.isLoading && state.hasNextPage) {
                    performSearch(state.query, isInitial = false)
                }
            }
        }
    }

    private fun performSearch(query: String, isInitial: Boolean) {
        if (query.isBlank()) {
            state = state.copy(
                animeList = emptyList(),
                isLoading = false,
                isInitialLoading = false,
                currentPage = 1,
                hasNextPage = true,
                error = null
            )
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                if (isInitial) {
                    state = state.copy(
                        isLoading = true,
                        isInitialLoading = true,
                        currentPage = 1,
                        animeList = emptyList(),
                        error = null
                    )
                } else {
                    state = state.copy(
                        isLoading = true,
                        error = null
                    )
                }

                val result = withTimeoutOrNull(SEARCH_TIMEOUT) {
                    searchUseCase(query.lowercase())
                }

                if (result == null) {
                    state = state.copy(
                        isLoading = false,
                        isInitialLoading = false,
                        error = "Превышено время ожидания. Попробуйте еще раз."
                    )
                    return@launch
                }

                result.onSuccess { anime ->
                    state = state.copy(
                        animeList = if (isInitial) anime else state.animeList + anime,
                        isLoading = false,
                        isInitialLoading = false,
                        hasNextPage = anime.isNotEmpty(),
                        error = null
                    )
                }.onFailure { error ->
                    Log.e("SearchScreenViewModel", "${error.message}")
                    state = state.copy(
                        isLoading = false,
                        isInitialLoading = false,
                        error = "Ошибка поиска: ${error.message ?: "Неизвестная ошибка"}"
                    )
                }
            } catch (e: Exception) {
                Log.e("SearchScreenViewModel", "${e.message}")
                state = state.copy(
                    isLoading = false,
                    isInitialLoading = false,
                    error = "Произошла ошибка: ${e.message ?: "Неизвестная ошибка"}"
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}

class SearchScreenViewModelFactory(
    private val repository: AnimeSearchUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchScreenViewModel::class.java)) {
            return SearchScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}