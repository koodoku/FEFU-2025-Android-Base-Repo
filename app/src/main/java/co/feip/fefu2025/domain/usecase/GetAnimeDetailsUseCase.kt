package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl

class GetAnimeDetailsUseCase() {
    private val repository = AnimeRepositoryImpl()
    operator fun invoke(animeId: Int): AnimeDetails? = repository.getAnimeById(animeId)
}