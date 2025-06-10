package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.mappers.mapRecommendationsToDomain
import co.feip.fefu2025.data.mappers.mapTopAnimeToDomain
import co.feip.fefu2025.data.mappers.toDomain
import co.feip.fefu2025.data.remoute.RetrofitInstance
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.repository.AnimeRepository

class AnimeRepositoryImpl: AnimeRepository {
    private val api = RetrofitInstance.animeApi

    override suspend fun getTopAnimeList(): List<Anime> {
        return api.getTopAnime().mapTopAnimeToDomain()
    }

    override suspend fun getAnimeById(id: Int, recommendations: List<Anime>): AnimeDetails? {
        return api.getAnimeById(id).toDomain(recommendations)
    }

    override suspend fun animeSearch(query: String): List<Anime> {
        return api.searchAnime(query).mapTopAnimeToDomain()
    }

    override suspend fun getRecommendationsAnime(): List<Anime> {
        return api.getRecommendationsAnime().mapRecommendationsToDomain()
    }
}


