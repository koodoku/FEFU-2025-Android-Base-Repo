package co.feip.fefu2025.presentation.screen.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.usecase.GetAnimeDetailsUseCase
import co.feip.fefu2025.presentation.util.UiState
import kotlinx.coroutines.launch

class AnimeDetailsViewModel(val animeId: Int, val animeDetailsUseCase: GetAnimeDetailsUseCase) : ViewModel() {
    private val getAnimeDetailsUseCase = animeDetailsUseCase

    var animeDetailsState by mutableStateOf(DetailsScreenState())
        private set
    init {
        loadAnimeDetails()
    }
    fun loadAnimeDetails() {
        animeDetailsState = animeDetailsState.copy(isLoading = true)
        viewModelScope.launch {
             val details = getAnimeDetailsUseCase(animeId)
            details.onSuccess { animeDetails ->
                animeDetailsState = animeDetailsState.copy(
                    isLoading = false,
                    animeDetails = animeDetails
                )
            }.onFailure { error ->
                Log.e("AnimeDetailsViewModel", error.message.toString())
                animeDetailsState = animeDetailsState.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        }
    }
}
class AnimeDetailsViewModelFactory(
    private val animeId: Int,
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeDetailsViewModel(animeId, getAnimeDetailsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}