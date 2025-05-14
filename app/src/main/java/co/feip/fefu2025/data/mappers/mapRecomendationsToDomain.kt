package co.feip.fefu2025.data.mappers

import co.feip.fefu2025.data.remoute.dto.recomendations.RecomendationsDto
import co.feip.fefu2025.domain.model.Anime

fun RecomendationsDto.mapRecommendationsToDomain(): List<Anime> {
    return data.flatMap { dataEntry ->
        dataEntry.entry.map { entry ->
            Anime(
                id = entry.mal_id,
                title = entry.title,
                genres = emptyList(),
                rating = null,
                image = entry.images.jpg.image_url,
                year = null,
                episodes = null,
                description = null
            )
        }
    }
}