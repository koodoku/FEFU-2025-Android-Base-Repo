package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl

class GetAnimeDetailsUseCase() {
    private val repository = AnimeRepositoryImpl()
    suspend operator fun invoke(animeId: Int): Result<AnimeDetails?> {
        return try {
            val recommendations = repository.getRecommendationsAnime()
            val animeDetails = repository.getAnimeById(animeId, recommendations)
            return Result.success(animeDetails)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}