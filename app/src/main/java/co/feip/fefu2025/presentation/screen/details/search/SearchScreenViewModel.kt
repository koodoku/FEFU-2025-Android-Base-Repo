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
import kotlinx.coroutines.launch

class SearchScreenViewModel(val searchUseCase: AnimeSearchUseCase): ViewModel() {
    var state by mutableStateOf(SearchScreenState())
        private set

    private var searchJob: Job? = null

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.clearQuery -> {
                state = state.copy(query = "", animeList = emptyList())
            }
            is SearchScreenEvent.onSearch -> {
                updateQuery(event.query)
            }
        }
    }


    private fun updateQuery(query: String) {
        viewModelScope.launch {
            state = state.copy(query = query, isLoading = true)
            if (state.query.isNotEmpty()) {
                searchUseCase(query.lowercase())
                    .onSuccess { anime ->
                        state = state.copy(
                            animeList = anime,
                            isLoading = false
                        )
                    }
                    .onFailure {
                        Log.e("SearchScreenViewModel", "${it.message}")
                        state = state.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }
            } else {
                searchJob?.cancel()
                state = state.copy(
                    animeList = emptyList(),
                    isLoading = false
                )
            }
        }

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