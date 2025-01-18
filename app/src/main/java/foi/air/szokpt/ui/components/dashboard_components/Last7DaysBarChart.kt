package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.*

data class DayTransaction(
    val dayName: String,
    val dayDate: String,
    val count: Int
)

@Composable
fun Last7DaysTransactionsBarChart(
    dayTransactionData: List<DayTransaction>,
    barColor: Color = Secondary,
    maxBarHeight: Int = 100,
    textColor: Color = TextWhite
) {
    val maxTransactions = dayTransactionData.maxOfOrNull { it.count }?.coerceAtLeast(1) ?: 1

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        dayTransactionData.forEach { day ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = day.dayName,
                    color = textColor,
                    fontSize = 14.sp
                )

                val barFraction = day.count.toFloat() / maxTransactions.toFloat()
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .width(20.dp)
                        .height((maxBarHeight * barFraction).dp)
                        .background(barColor)
                )

                Text(
                    text = day.dayDate,
                    color = textColor,
                    fontSize = 14.sp
                )
            }
        }
    }
}
