package co.feip.fefu2025.data.remoute

import co.feip.fefu2025.data.remoute.dto.top_anime.TopAnimeDto
import retrofit2.http.GET

interface AnimeApi {
    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeDto
}