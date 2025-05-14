package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.model.Anime

class AnimeSearchUseCase {
    private val repository = AnimeRepositoryImpl()
    suspend operator fun invoke(query: String): Result<List<Anime>> {
        return try {
            val result = repository.animeSearch(query)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}