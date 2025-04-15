package co.feip.fefu2025.presentation.screen.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.launch
import java.time.temporal.TemporalQuery

class AnimeListViewModel() : ViewModel() {
    private val getAnimeListUseCase = GetAnimeListUseCase()

    var state by mutableStateOf<MainScreenState>(MainScreenState())
        private set

    init {
        loadAnimeList()
    }

    private fun loadAnimeList() {
        viewModelScope.launch {
            state = state.copy(
                animeList = getAnimeListUseCase()
            )
        }
    }

    fun onQueryChange(query: String){
        viewModelScope.launch {
            state = state.copy(
                searchQuery = query
            )
        }
    }
}
