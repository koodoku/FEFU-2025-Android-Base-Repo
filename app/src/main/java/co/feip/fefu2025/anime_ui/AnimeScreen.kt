package co.feip.fefu2025.anime_ui

import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import co.feip.fefu2025.CustomLayout
import co.feip.fefu2025.R

@Preview
@Composable
fun AnimeScreen(
    title: String = "Bocchi the Rock!",
    genres: List<String> = listOf("Комедия", "Музыка", "Повседневность"),
    rating: Float = 8.8f,
    description: String = "Хитори Гото с детства мечтает играть в рок-группе и ради этого чуть ли не в совершенстве овладела игрой на электрогитаре. К несчастью, исключительные навыки так и не принесли ей ни единого друга. Однако, возможно, её мечта осуществится благодаря встрече с Нидзикой Идзити — девушкой, которая играет на ударных и ищет гитариста для своей группы….",
    year: Int = 2022,
    episodes: Int = 12,
    imageRes: Int = R.drawable.boochi
) {
    val ratings = mapOf(
        1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 300,
        6 to 400, 7 to 600, 8 to 800, 9 to 700, 10 to 500
    )

    val recommendations = listOf(
        AnimeCardData("Ghost in the Shell", listOf("Киберпанк", "Экшен", "Детектив"), 8.3f, R.drawable.ghost_in_the_shell),
        AnimeCardData("Call of the Night", listOf("Романтика", "Сверхъестественное"), 8.1f, R.drawable.call_of_the_night),
        AnimeCardData("Nier Automata Ver 1.1", listOf("Фантастика", "Экшен", "Драма"), 8.5f, R.drawable.nier),
        AnimeCardData("Bocchi the Rock!", listOf("Комедия", "Музыка", "Повседневность"), 8.8f, R.drawable.boochi),
        AnimeCardData("Miss Kobayashi's Dragon Maid", listOf("Комедия", "Фэнтези", "Сэйнэн"), 8.4f, R.drawable.kobayashi),
        AnimeCardData("Nichijou: My Ordinary Life", listOf("Комедия", "Повседневность"), 8.6f, R.drawable.nichijou),
        AnimeCardData("My Dress-Up Darling", listOf("Драма", "Романтика", "Повседневность"), 8.3f, R.drawable.farfor),
        AnimeCardData("Horimiya", listOf("Романтика", "Комедия", "Школа"), 8.7f, R.drawable.horimiya),
        AnimeCardData("My Deer Friend Nokotan", listOf("Комедия", "Повседневность"), 8.2f, R.drawable.shikanoko),
        AnimeCardData("Mushoku Tensei: Jobless Reincarnation", listOf("Исекай", "Комедия", "Драма"), 8.0f, R.drawable.reink)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Основная инф
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Рейтинг: $rating", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Год: $year", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Эпизодов: $episodes", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Жанры:", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        val context = LocalContext.current
        val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant.toArgb()
        val textColor = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()

        AndroidView(
            factory = { ctx ->
                CustomLayout(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            },
            update = { customLayout ->
                customLayout.removeAllViews()
                genres.forEach { genre ->
                    val textView = android.widget.TextView(context).apply {
                        text = genre
                        setTextAppearance(android.R.style.TextAppearance_Material_Body2)
                        setTextColor(textColor)
                        setPadding(24, 12, 24, 12)
                        background = context.getDrawable(R.drawable.anime_background)?.mutate()?.apply {
                            setTint(surfaceVariantColor)
                        }
                    }
                    customLayout.addView(textView)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Описание:", style = MaterialTheme.typography.titleMedium)
        Text(description, style = MaterialTheme.typography.bodyLarge)

        // График рейтинга
        Spacer(modifier = Modifier.height(16.dp))
        RatingChart(ratings = ratings)

        // Рекомендации
        Spacer(modifier = Modifier.height(24.dp))
        Text("Может понравиться", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(recommendations) { anime ->
                AnimeCard(
                    title = anime.title,
                    genres = anime.genres,
                    rating = anime.rating,
                    imageRes = anime.imageRes,
                    modifier = Modifier.width(160.dp)
                )
            }
        }
    }
}
