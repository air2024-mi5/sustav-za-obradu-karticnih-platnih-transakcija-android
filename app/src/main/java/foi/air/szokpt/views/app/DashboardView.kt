package foi.air.szokpt.views.app

import CardBrandsBarChart
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.helpers.TransactionUtils
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.dashboard_components.BarComponent
import foi.air.szokpt.ui.components.dashboard_components.ChartWithLegend
import foi.air.szokpt.ui.components.dashboard_components.CustomCircularProgressBar
import foi.air.szokpt.ui.components.dashboard_components.TransactionsPerDay
import foi.air.szokpt.ui.components.dashboard_components.TransationsOverviewComponent
import foi.air.szokpt.ui.theme.Alternative
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.utils.CardBrandInformationList
import foi.air.szokpt.viewmodels.CardBrandsStatisticsViewModel
import foi.air.szokpt.viewmodels.ReportsViewModel
import java.time.LocalDate

@Composable
fun DashboardView(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            AllTransactionsTile()
        }
        item(span = { GridItemSpan(2) }) {
            ValueTile()
        }
        item(span = { GridItemSpan(2) }) {
            CardBrandsTile(transactionUtils = TransactionUtils)
        }
        item(span = { GridItemSpan(2) }) {
            TransactionsListTile()
        }
        item(span = { GridItemSpan(2) }) {
            TransactionOutcomes()
        }
        item(span = { GridItemSpan(2) }) {
            TransactionsByDayTile()
        }
    }
}

@Composable
fun ValueTile() {
    TileSegment(
        tileSizeMode = TileSizeMode.FILL_MAX_SIZE,
        innerPadding = 4.dp,
        outerMargin = 8.dp,
        minWidth = 150.dp,
        minHeight = 200.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Value",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "€1 002 123",
                    color = success,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "This week:",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "-9.8%",
                    color = danger,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "+ €26 017",
                    color = success,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AllTransactionsTile() {
    val sampleDayData = listOf(
        TransactionsPerDay(LocalDate.of(2024, 1,13), 14),
        TransactionsPerDay(LocalDate.of(2024, 1,14), 28),
        TransactionsPerDay(LocalDate.of(2024, 1,15), 8),
        TransactionsPerDay(LocalDate.of(2024, 1,16), 14),
        TransactionsPerDay(LocalDate.of(2024, 1,17), 28),
        TransactionsPerDay(LocalDate.of(2024, 1,18), 8),
        TransactionsPerDay(LocalDate.of(2024, 1,19), 8),
    )
    TransationsOverviewComponent(sampleDayData)
}

@Composable
fun TextRow(label: String, value: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            color = color,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TransactionsListTile() {
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 16.dp,
        outerMargin = 8.dp,
        minWidth = 250.dp,
        minHeight = 20.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Transactions list",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

@Composable
fun CardBrandsTile(
    transactionUtils: TransactionUtils,
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

@Composable
fun TransactionsByDayTile() {
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 16.dp,
        outerMargin = 8.dp,
        minWidth = 250.dp,
        minHeight = 200.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Transaction types",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val transactions = listOf(60.dp, 25.dp, 30.dp, 35.dp, 55.dp, 80.dp, 15.dp)
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

                transactions.forEachIndexed { index, height ->
                    BarComponent(
                        height = height,
                        color = Primary,
                        label = days[index],
                        value = ""
                    )
                }
            }
        }
    }
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