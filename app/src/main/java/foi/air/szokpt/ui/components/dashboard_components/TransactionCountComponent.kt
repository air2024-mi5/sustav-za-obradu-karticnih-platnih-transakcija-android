package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.success

@Composable
fun TransationsOverviewComponent() {
    TileSegment(
        innerPadding = 16.dp,
        outerMargin = 8.dp,
        minWidth = 250.dp,
        minHeight = 40.dp,
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        color = BGLevelOne
    ) {
        val sampleDayData = listOf(
            DayTransaction("Mon", "16.1", 30),
            DayTransaction("Tue", "17.1", 5),
            DayTransaction("Wed", "18.1", 14),
            DayTransaction("Thu", "19.1", 21),
            DayTransaction("Fri", "20.1", 14),
            DayTransaction("Sat", "21.1", 28),
            DayTransaction("Sun", "22.1", 8),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Transactions overview",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Last7DaysTransactionsBarChart(
                    dayTransactionData = sampleDayData,
                    maxBarHeight = 100,
                    textColor = TextWhite
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Success Rate",
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Box(contentAlignment = Alignment.Center) {
                        val successFraction = 0.7f
                        CustomCircularProgressBar(
                            progress = successFraction,
                            modifier = Modifier.size(90.dp),
                            strokeWidth = 6.dp,
                            progressColor = success,
                            backgroundColor = Secondary
                        )
                        Text(
                            text = "${(successFraction * 100).toInt()}%",
                            color = TextWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    MaterialTheme {
        Surface {
            TransationsOverviewComponent()
        }
    }
}