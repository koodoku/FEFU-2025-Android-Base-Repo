package co.feip.fefu2025.presentation.screen.main_screen

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class AnimeListViewModel() : ViewModel() {
    private val repository = AnimeRepositoryImpl()
    private val getAnimeListUseCase = GetAnimeListUseCase(repository)

    var animeListState by mutableStateOf(MainScreenState())
        private set

    var searchQuery by mutableStateOf("")

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                // Устанавливаем таймаут в 5 секунд для начальной загрузки
                withTimeout(5000) {
                    val anime = getAnimeListUseCase()
                    if (anime.isSuccess) {
                        animeListState = animeListState.copy(
                            isLoading = false,
                            animeList = anime.getOrNull()?.take(10) ?: emptyList() // Загружаем только первые 10 элементов
                        )
                    } else {
                        animeListState = animeListState.copy(
                            isLoading = false,
                            error = anime.exceptionOrNull()?.message
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("AnimeListViewModel", "Error loading initial data", e)
                animeListState = animeListState.copy(
                    isLoading = false,
                    error = "Ошибка загрузки данных"
                )
            }
        }
    }

    fun loadAnimeList() {
        animeListState = animeListState.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val anime = getAnimeListUseCase()
                animeListState = if (anime.isSuccess) {
                    animeListState.copy(
                        isLoading = false,
                        animeList = anime.getOrNull() ?: emptyList()
                    )
                } else {
                    Log.e("AnimeListViewModel", "${anime.exceptionOrNull()?.message}")
                    animeListState.copy(
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

