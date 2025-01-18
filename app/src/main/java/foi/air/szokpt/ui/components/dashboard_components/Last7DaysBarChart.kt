package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TransactionsPerDay(
    val date: LocalDate,
    val count: Int
)

@Composable
fun Last7DaysTransactionsBarChart(
    transactionsPerDay: List<TransactionsPerDay>,
    maxBarHeight: Int = 100,
    barColor: Color = Primary,
    textColor: Color = TextWhite
) {
    val maxTransactions = transactionsPerDay.maxOfOrNull { it.count }?.coerceAtLeast(1) ?: 1

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        transactionsPerDay.forEachIndexed { index, day ->
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = day.count.toString(),
                    color = textColor,
                    fontSize = 12.sp
                )
                val barFraction = day.count.toFloat() / maxTransactions.toFloat()
                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(barColor, barColor.copy(alpha = 0.66f))
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .width(15.dp)
                        .height((maxBarHeight * barFraction).dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 5.dp,
                                topEnd = 5.dp
                            )
                        )
                        .background(brush = gradientBrush)
                )
                Text(
                    text = day.date.format(DateTimeFormatter.ofPattern("dd.MM")),
                    color = textColor,
                    fontSize = 12.sp
                )
            }
        }
    }
}
