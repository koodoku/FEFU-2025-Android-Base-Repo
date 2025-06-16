package co.feip.fefu2025.data.remoute

import co.feip.fefu2025.data.remoute.dto.anime_details.AnimeDetailsDto
import co.feip.fefu2025.data.remoute.dto.recomendations.RecomendationsDto
import co.feip.fefu2025.data.remoute.dto.top_anime.TopAnimeDto
import co.feip.fefu2025.domain.model.Anime
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeDto

    @GET("anime/{id}/recommendations")
    suspend fun getAnimeRecommendations(
        @Path("id") id: Int,
        @Query("page") page: Int = 1
    ): RecomendationsDto

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id")id: Int): AnimeDetailsDto

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): TopAnimeDto
}