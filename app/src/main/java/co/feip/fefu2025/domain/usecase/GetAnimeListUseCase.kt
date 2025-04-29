package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl

class GetAnimeListUseCase(
    private val repository: AnimeRepositoryImpl
) {
    suspend operator fun invoke(): List<Anime> = repository.getAnimeList()
    suspend operator fun invoke(genres: List<String>): List<Anime> = repository.getSimilarAnimeByGenres(genres)
}