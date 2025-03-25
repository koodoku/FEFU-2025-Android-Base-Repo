package co.feip.fefu2025.anime_ui

import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun AnimeScreen(
    title: String,
    genres: List<String>,
    rating: Float,
    description: String,
    year: Int,
    episodes: Int,
    imageRes: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
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

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Рейтинг:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = rating.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Год: $year",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Эпизодов: $episodes",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Жанры:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
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
                    val textView = TextView(context).apply {
                        text = genre
                        setTextAppearance(android.R.style.TextAppearance_Material_Body2)
                        setTextColor(textColor)
                        setPadding(32, 16, 32, 16)
                        background = context.getDrawable(R.drawable.anime_background)?.mutate()?.apply {
                            setTint(surfaceVariantColor)
                        }
                        setPadding(24, 12, 24, 12)
                    }
                    customLayout.addView(textView)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Описание:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAnimeScreen() {
    MaterialTheme {
        AnimeScreen(
            title = "Bocchi the Rock!",
            genres = listOf("Комедия", "Музыка", "Повседневность", "Сёнэн", "Школа"),
            rating = 8.8f,
            description = "Хитори Гото - крайне застенчивая девушка, которая мечтает стать рок-звездой. " +
                    "Когда её приглашают присоединиться к группе Kessoku Band, она начинает свой путь " +
                    "к преодолению социальной тревожности и реализации своей мечты.",
            year = 2022,
            episodes = 12,
            imageRes = R.drawable.boochi
        )
    }
}