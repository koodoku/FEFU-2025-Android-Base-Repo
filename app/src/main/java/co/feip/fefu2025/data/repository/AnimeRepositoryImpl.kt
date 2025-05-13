package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.mappers.mapRecommendationsToDomain
import co.feip.fefu2025.data.mappers.mapTopAnimeToDomain
import co.feip.fefu2025.data.mappers.toDomain
import co.feip.fefu2025.data.remoute.RetrofitInstance
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.delay

class AnimeRepositoryImpl: AnimeRepository {
    val api = RetrofitInstance.animeApi
    private var getAnimeListCallCount = 0
    private var getAnimeByIdCallCount = 0

    override suspend fun getTopAnimeList(): List<Anime> {
        val anime = api.getTopAnime().mapTopAnimeToDomain()
        return anime
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


//    override fun getSimilarAnimeByGenres(genres: List<String>): List<Anime> {
//        return animeList.filter { anime ->
//            anime.genres.any { it in genres }
//        }
//    }

//    override suspend fun getAnimeById(id: Int): AnimeDetails {
//        delay(1000)
//        getAnimeByIdCallCount++
//        if (getAnimeByIdCallCount % 3 == 0) {
//            throw Exception("Ошибка загрузки информации об аниме")
//        }
//
//        val anime = animeList.find { it.id == id }
//            ?: throw Exception("Аниме не найдено")
//
//        val similarAnime = getSimilarAnimeByGenres(anime.genres, animeList)
//
//        return AnimeDetails(
//            id = anime.id,
//            title = anime.title,
//            genres = anime.genres,
//            rating = anime.rating,
//            imageRes = anime.image,
//            year = anime.year,
//            episodes = anime.episodes,
//            description = anime.description,
//            similar = similarAnime
//        )
//    }
}

private var getSimilarCallCount = 0

suspend fun getSimilarAnimeByGenres(genres: List<String>, animeList: List<Anime>): List<Anime> {
    delay(1000)
    getSimilarCallCount++
    if (getSimilarCallCount % 3 == 0) {
        throw Exception("Ошибка загрузки похожего аниме")
    }
    return animeList.filter { anime ->
        anime.genres.any { it in genres }
    }
}


