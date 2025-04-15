package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails

interface AnimeRepository {
    fun getAnimeList(): List<Anime>
    fun getAnimeById(id: Int): AnimeDetails?
}