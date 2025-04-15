package co.feip.fefu2025.presentation.screen.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailsUseCase
import kotlinx.coroutines.launch

class AnimeDetailsViewModel(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase
) : ViewModel() {
    private val _anime = mutableStateOf<Anime?>(null)
    val anime = _anime

    private val _recommendations = mutableStateOf<List<Anime>>(emptyList())
    val recommendations = _recommendations

    fun loadAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            _anime.value = getAnimeDetailsUseCase(animeId)
            _anime.value?.let {
                _recommendations.value = getAnimeDetailsUseCase.getRecommendations(it.id)
            }
        }
    }
}