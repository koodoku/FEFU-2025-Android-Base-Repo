package co.feip.fefu2025.presentation.screen.main_screen

import android.util.Log
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
        if (animeListState.isLoading) return
        
        animeListState = animeListState.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val anime = getAnimeListUseCase()
                if (anime.isSuccess) {
                    val newList = anime.getOrNull() ?: emptyList()
                    animeListState = animeListState.copy(
                        isLoading = false,
                        animeList = if (animeListState.currentPage == 1) {
                            newList
                        } else {
                            animeListState.animeList + newList
                        },
                        hasNextPage = newList.isNotEmpty(),
                        currentPage = animeListState.currentPage + 1
                    )
                } else {
                    Log.e("AnimeListViewModel", "${anime.exceptionOrNull()?.message}")
                    animeListState = animeListState.copy(
                        isLoading = false,
                        error = anime.exceptionOrNull()?.message
                    )
                }
            } catch (e: Exception) {
                animeListState = animeListState.copy(
                    isLoading = false,
                    error = "Ошибка загрузки данных"
                )
            }
        }
    }
}

