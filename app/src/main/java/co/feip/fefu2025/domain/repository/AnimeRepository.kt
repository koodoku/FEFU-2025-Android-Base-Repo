package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.domain.model.Anime

class AnimeRepository {
    private val animeList = listOf(
        Anime(
            id = 1,
            title = "Ghost in the Shell",
            genres = listOf("Киберпанк", "Фантастика", "Детектив"),
            rating = 8.3f,
            imageRes = R.drawable.ghost_in_the_shell,
            year = 1995,
            episodes = 1,
            description = "Классика киберпанка о борьбе с киберпреступностью в будущем. " +
                    "Майор Мотоко Кусанаги и её команда из Секции 9 расследуют сложные киберпреступления."
        ),
        Anime(
            id = 2,
            title = "Call of the Night",
            genres = listOf("Романтика", "Сверхъестественное", "Драма"),
            rating = 7.8f,
            imageRes = R.drawable.call_of_the_night,
            year = 2022,
            episodes = 13,
            description = "История о подростке Кодзи, который встречает загадочную девушку-вампира Надзуну. " +
                    "Вместе они исследуют ночной мир и его тайны."
        ),
        Anime(
            id = 3,
            title = "NieR:Automata Ver 1.1",
            genres = listOf("Фантастика", "Экшен", "Драма", "Постапокалипсис"),
            rating = 8.5f,
            imageRes = R.drawable.nier,
            year = 2023,
            episodes = 12,
            description = "Аниме-адаптация культовой игры о войне андроидов. " +
                    "История андроидов 2B, 9S и A2, сражающихся за будущее человечества."
        ),
        Anime(
            id = 4,
            title = "Bocchi the Rock!",
            genres = listOf("Комедия", "Музыка", "Повседневность"),
            rating = 8.8f,
            imageRes = R.drawable.boochi,
            year = 2022,
            episodes = 12,
            description = "История о застенчивой девушке Хитаре Гото, мечтающей стать рок-звездой. " +
                    "Она преодолевает свою социофобию, играя в группе."
        )
    )

    fun getAnimeList(): List<Anime> = animeList

    fun getAnimeById(id: Int): Anime? = animeList.find { it.id == id }

    fun getRecommendations(currentAnimeId: Int): List<Anime> =
        animeList.filter { it.id != currentAnimeId }.shuffled().take(4)

    fun searchAnime(query: String): List<Anime> {
        return if (query.isEmpty()) {
            animeList
        } else {
            animeList.filter { anime ->
                anime.title.contains(query, ignoreCase = true) ||
                        anime.genres.any { it.contains(query, ignoreCase = true) }
            }
        }
    }
}