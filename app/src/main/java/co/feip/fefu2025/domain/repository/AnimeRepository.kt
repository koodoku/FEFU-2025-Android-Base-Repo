package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails

interface AnimeRepository {
    suspend fun getTopAnimeList(): List<Anime>
    suspend fun getAnimeById(id: Int): AnimeDetails?
    fun getSimilarAnimeByGenres(strings: List<String>): List<Anime>
}