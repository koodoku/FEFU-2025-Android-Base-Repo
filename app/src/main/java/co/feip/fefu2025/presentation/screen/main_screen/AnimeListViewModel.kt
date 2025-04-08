package co.feip.fefu2025.presentation.screen.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _animeList = mutableStateOf<List<Anime>>(emptyList())
    val animeList: State<List<Anime>> = _animeList

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadAnimeList()
    }

    fun loadAnimeList() {
        viewModelScope.launch {
            _animeList.value = getAnimeListUseCase()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredAnimeList(): List<Anime> {
        return if (searchQuery.value.isEmpty()) {
            animeList.value
        } else {
            animeList.value.filter {
                it.title.contains(searchQuery.value, ignoreCase = true)
            }
        }
    }
}
