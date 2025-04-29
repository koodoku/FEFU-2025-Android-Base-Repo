package co.feip.fefu2025.presentation.screen.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.presentation.util.UiState
import kotlinx.coroutines.launch
import java.time.temporal.TemporalQuery
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import kotlinx.coroutines.launch

class AnimeListViewModel() : ViewModel() {
    private val repository = AnimeRepositoryImpl()
    private val getAnimeListUseCase = GetAnimeListUseCase(repository)

    //var state by mutableStateOf<MainScreenState>(MainScreenState())
    var animeListState by mutableStateOf<UiState<List<Anime>>>(UiState.Loading)
        private set

    var searchQuery by mutableStateOf("")

    init {
        loadAnimeList()
    }

//    private fun loadAnimeList() {
//        viewModelScope.launch {
//            state = state.copy(
//                animeList = getAnimeListUseCase()
//            )
//        }
//    }
fun loadAnimeList() {
    animeListState = UiState.Loading
    viewModelScope.launch {
        try {
            val result = getAnimeListUseCase()
            animeListState = UiState.Success(result)
        } catch (e: Exception) {
            animeListState = UiState.Error("Ошибка загрузки аниме")
        }
    }

    fun onQueryChange(query: String){
        searchQuery = query
        }

    fun retry(){
        loadAnimeList()
    }
}

    }

