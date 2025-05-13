package co.feip.fefu2025.presentation.screen.main_screen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.launch

class AnimeListViewModel() : ViewModel() {
    private val repository = AnimeRepositoryImpl()
    private val getAnimeListUseCase = GetAnimeListUseCase(repository)

    var animeListState by mutableStateOf(MainScreenState())
        private set

    var searchQuery by mutableStateOf("")

    init {
        loadAnimeList()
    }

    fun loadAnimeList() {
        animeListState = animeListState.copy(isLoading = true)
        viewModelScope.launch {
            val anime = getAnimeListUseCase()
            animeListState = if (anime.isSuccess) {
                animeListState.copy(
                    isLoading = false,
                    animeList = anime.getOrNull() ?: emptyList()
                )
            } else {
                animeListState.copy(
                    isLoading = false,
                    error = anime.exceptionOrNull()?.message
                )
            }
        }

    }
}

