package co.feip.fefu2025.data.mappers

import co.feip.fefu2025.data.remoute.dto.anime_details.AnimeDetailsDto
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails

fun AnimeDetailsDto.toDomain(recomendations: List<Anime>): AnimeDetails {
    return data.let {
        AnimeDetails(
            id = it.mal_id,
            title = it.title,
            genres = it.genres.map { genre ->
                genre.name
            },
            rating = transformDoubleToFloat(it.score),
            imageRes = it.images.jpg.image_url,
            year = it.year,
            episodes = it.episodes,
            description = it.synopsis,
            similar = recomendations
        )
    }
}