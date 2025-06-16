package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails

interface AnimeRepository {
    suspend fun getTopAnimeList(): List<Anime>
    suspend fun getAnimeById(id: Int, recommendations: List<Anime>): AnimeDetails?
    suspend fun animeSearch(query: String): List<Anime>
    suspend fun getAnimeRecommendations(animeId: Int, page: Int = 1): List<Anime>
}