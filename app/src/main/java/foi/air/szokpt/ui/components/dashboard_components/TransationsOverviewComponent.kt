package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import java.time.LocalDate

@Composable
fun TransactionsOverviewComponent(
    transactionsPerDay: List<TransactionsPerDay>,
    errorMessage: String
) {
    TileSegment(
        innerPadding = 16.dp,
        outerMargin = 8.dp,
        minWidth = 250.dp,
        minHeight = 40.dp,
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Transactions Overview",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                when (errorMessage) {
                    "" -> {
                        Last7DaysTransactionsBarChart(
                            transactionsPerDay = transactionsPerDay,
                            maxBarHeight = 100,
                            textColor = TextWhite
                        )
                    }

                    else -> {
                        Text(
                            text = errorMessage,
                            color = TextWhite,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(5.dp)
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
    val sampleDayData = listOf(
        TransactionsPerDay(LocalDate.of(2024, 1, 13), 14),
        TransactionsPerDay(LocalDate.of(2024, 1, 14), 28),
        TransactionsPerDay(LocalDate.of(2024, 1, 15), 8),
        TransactionsPerDay(LocalDate.of(2024, 1, 16), 14),
        TransactionsPerDay(LocalDate.of(2024, 1, 17), 28),
        TransactionsPerDay(LocalDate.of(2024, 1, 18), 8),
        TransactionsPerDay(LocalDate.of(2024, 1, 19), 8),
    )
    MaterialTheme {
        Surface {
            TransactionsOverviewComponent(sampleDayData, "")
        }
    }
}