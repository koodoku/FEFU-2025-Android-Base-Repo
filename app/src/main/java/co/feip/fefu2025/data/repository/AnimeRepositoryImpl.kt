package co.feip.fefu2025.data.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.repository.AnimeRepository

class AnimeRepositoryImpl: AnimeRepository {
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
        ),
        Anime(
            id = 5,
            title = "Miss Kobayashi's Dragon Maid",
            genres = listOf("Комедия", "Фэнтези", "Сэйнэн"),
            rating = 8.4f,
            imageRes = R.drawable.kobayashi,
            year = 2017,
            episodes = 13,
            description = "Офисный работник Кобаяши однажды спасает дракона, который превращается в милую девушку-горничную."
        ),
        Anime(
            id = 6,
            title = "Nichijou: My Ordinary Life",
            genres = listOf("Комедия", "Повседневность"),
            rating = 8.6f,
            imageRes = R.drawable.nichijou,
            year = 2011,
            episodes = 26,
            description = "Абсурдная комедия о повседневной жизни школьников, где обычные ситуации превращаются в нечто невероятное."
        ),
        Anime(
            id = 7,
            title = "My Dress-Up Darling",
            genres = listOf("Драма", "Романтика", "Повседневность"),
            rating = 8.3f,
            imageRes = R.drawable.farfor,
            year = 2022,
            episodes = 12,
            description = "История о парне, увлекающемся созданием традиционных кукол, и девушке-косплеере, которые находят общий язык через хобби."
        ),
        Anime(
            id = 8,
            title = "Horimiya",
            genres = listOf("Романтика", "Комедия", "Школа"),
            rating = 8.7f,
            imageRes = R.drawable.horimiya,
            year = 2021,
            episodes = 13,
            description = "История двух школьников, которые вне школы ведут совершенно другую жизнь, и их неожиданной дружбы."
        ),
        Anime(
            id = 9,
            title = "My Deer Friend Nokotan",
            genres = listOf("Комедия", "Повседневность"),
            rating = 8.2f,
            imageRes = R.drawable.shikanoko,
            year = 2023,
            episodes = 12,
            description = "Эксцентричная комедия о девушке, которая считает себя оленихой и её необычных приключениях."
        ),
        Anime(
            id = 10,
            title = "Mushoku Tensei: Jobless Reincarnation",
            genres = listOf("Исекай", "Комедия", "Драма"),
            rating = 8.0f,
            imageRes = R.drawable.reink,
            year = 2021,
            episodes = 23,
            description = "История безработного, который после смерти перерождается в мире фэнтези с сохранением воспоминаний."
        )
    )

    override fun getAnimeList(): List<Anime> = animeList

    fun getSimilarAnimeByGenres(genres: List<String>): List<Anime> {
        return animeList.filter { anime ->
            anime.genres.any { it in genres }
        }
    }

    override fun getAnimeById(id: Int): AnimeDetails? {
        val anime = animeList.find { it.id == id } ?: return null
        return AnimeDetails(
            id = anime.id,
            title = anime.title,
            genres = anime.genres,
            rating = anime.rating,
            imageRes = anime.imageRes,
            year = anime.year,
            episodes = anime.episodes,
            description = anime.description,
            similar = animeList.filter { it.id != id }
        )
    }

}