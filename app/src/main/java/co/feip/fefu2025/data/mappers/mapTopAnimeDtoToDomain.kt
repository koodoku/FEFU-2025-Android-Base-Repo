package co.feip.fefu2025.data.mappers

import co.feip.fefu2025.data.remoute.dto.top_anime.TopAnimeDto
import co.feip.fefu2025.domain.model.Anime
import kotlin.math.roundToInt

fun TopAnimeDto.mapTopAnimeToDomain(): List<Anime> {
    val result = this.data.map { anime ->
        anime.let { data ->
            Anime(
                id = data.mal_id,
                title = data.title,
                genres = data.genres.map { it.name },
                rating =  transformDoubleToFloat(data.score),
                image = data.images.jpg.image_url,
                year = data.year,
                episodes = data.episodes,
                description = data.synopsis,
            )
        }
    }
    return result
}

fun transformDoubleToFloat(value: Double): Float{
    return (value.toFloat() * 10).roundToInt() / 10f
}