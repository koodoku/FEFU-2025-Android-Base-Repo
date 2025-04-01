package co.feip.fefu2025.anime_ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RatingChart(
    ratings: Map<Int, Int>,
    modifier: Modifier = Modifier
) {
    if (ratings.isEmpty()) return

    val maxValue = ratings.values.maxOrNull() ?: 0
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Оценки пользователей",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            color = textColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            ratings.entries.sortedBy { it.key }.forEach { (rating, count) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    val barHeight = if (maxValue > 0) {
                        (count.toFloat() / maxValue * 150).dp
                    } else {
                        0.dp
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(150.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            val color = when {
                                rating <= 2 -> Color(0xFFFF0000)
                                rating <= 4 -> Color(0xFFFFA500)
                                rating <= 7 -> Color(0xFFFFFF00)
                                rating <= 8-> Color(0xFF32CD32)
                                else -> Color(0xFF32CD32)
                            }

                            val barTop = size.height - (barHeight.toPx())
                            drawRect(
                                color = color,
                                topLeft = Offset(0f, barTop),
                                size = size.copy(height = barHeight.toPx())
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "$count",  // кол во "отзывов"
                        style = MaterialTheme.typography.labelSmall,
                        color = textColor.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "$rating",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun RatingChartPreview() {
    val ratings = mapOf(
        1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 300,
        6 to 400, 7 to 600, 8 to 800, 9 to 700, 10 to 500
    )
    RatingChart(ratings = ratings, modifier = Modifier.padding(16.dp))
}
