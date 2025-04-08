package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAnimeDetailsUseCase(private val repository: AnimeRepository) {
    operator fun invoke(animeId: Int): Anime? = repository.getAnimeById(animeId)
    fun getRecommendations(currentAnimeId: Int): List<Anime> =
        repository.getRecommendations(currentAnimeId)
}