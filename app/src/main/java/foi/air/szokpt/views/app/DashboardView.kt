package foi.air.szokpt.views.app

import CardBrandsBarChart
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import foi.air.szokpt.ui.components.IconMessage
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.dashboard_components.ChartWithLegend
import foi.air.szokpt.ui.components.dashboard_components.TransactionsOverviewComponent
import foi.air.szokpt.ui.components.dashboard_components.TransactionsPerDay
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.utils.CardBrandInformationList
import foi.air.szokpt.viewmodels.CardBrandsStatisticsViewModel
import foi.air.szokpt.viewmodels.ReportsViewModel
import foi.air.szokpt.viewmodels.TransactionsPerDayViewModel
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun DashboardView() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            TransactionsPerDayTile()
        }
        item(span = { GridItemSpan(2) }) {
            CardBrandsTile()
        }
        item(span = { GridItemSpan(2) }) {
            TransactionOutcomes()
        }
    }
}

@Composable
fun CardBrandsTile(
    viewModel: CardBrandsStatisticsViewModel = viewModel()
) {
    val cardBrandsStats by viewModel.cardBrandsStatisticsData.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    val cardBrandInformationList = CardBrandInformationList()

    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 16.dp,
        outerMargin = 8.dp,
        minWidth = 150.dp,
        minHeight = 200.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Card Types",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                viewModel.fetchCardBrandsStatistics()

                when (errorMessage) {
                    null -> {
                        val cardBrandsList = cardBrandsStats?.let {
                            cardBrandInformationList.getCardBrandInformationList(
                                it
                            )
                        }

                        if (cardBrandsList != null) {
                            CardBrandsBarChart(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                stats = cardBrandsList,
                            )
                        }
                    }

                    else -> {
                        Text(
                            text = errorMessage.toString(),
                            color = TextWhite,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun TransactionsPerDayTile(
    viewModel: TransactionsPerDayViewModel = viewModel()
) {
    val errorMessage by viewModel.errorMessage.observeAsState()
    val transactionsPerDay by viewModel.transactionsPerDay.observeAsState()

    viewModel.fetchTransactionsPerDay()
    when (errorMessage) {
        null -> {
            val transactionData = transactionsPerDay?.map { (timestamp, count) ->
                TransactionsPerDay(
                    date = LocalDate.of(
                        timestamp.toLocalDateTime().year,
                        timestamp.toLocalDateTime().month,
                        timestamp.toLocalDateTime().dayOfMonth
                    ),
                    count = count
                )
            }
            if (transactionData != null) {
                TransactionsOverviewComponent(transactionData, "")
            }
        }

        else -> {
            IconMessage(
                "Couldn't display transactions per day.",
                errorMessage!!,
                Icons.Default.Clear
            )
        }
    }
}

private fun Timestamp.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this.time),
        ZoneId.systemDefault()
    )
}

@Composable
fun TransactionOutcomes() {
    val viewModel: ReportsViewModel = viewModel()
    val pieChartData by viewModel.pieChartData.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 16.dp,
        outerMargin = 8.dp,
        minWidth = 150.dp,
        minHeight = 200.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Transaction outcomes",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            viewModel.fetchTransactionsSuccess()
            when (errorMessage) {
                null -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ChartWithLegend(pieChartData)
                    }
                }

                else -> {
                    Text(
                        text = errorMessage.toString(),
                        color = TextWhite,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}