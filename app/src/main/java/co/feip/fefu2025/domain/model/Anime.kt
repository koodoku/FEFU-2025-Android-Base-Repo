package co.feip.fefu2025.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val rating: Float?,
    val image: String,
    val year: Int?,
    val episodes: Int?,
    val description: String?,
)