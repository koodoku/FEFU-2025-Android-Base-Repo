package co.feip.fefu2025.domain.model
data class AnimeDetails (
    val id: Int,
    val title: String,
    val genres: List<String>?,
    val rating: Float?,
    val imageRes: String,
    val year: Int?,
    val episodes: Int?,
    val description: String?,
    val similar: List<Anime>?
)

