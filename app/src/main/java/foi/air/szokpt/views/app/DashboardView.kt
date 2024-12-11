package foi.air.szokpt.views.app

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.theme.Alternative
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success

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
        item {
            ValueTile()
        }
        item {
            CardTypesTile()
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
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 16.dp,
        outerMargin = 8.dp,
        minWidth = 250.dp,
        minHeight = 20.dp,
        color = BGLevelOne
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "All Transactions",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextRow(label = "This week:", value = "9890", color = Primary)
                Spacer(modifier = Modifier.height(4.dp))
                TextRow(label = "Last Week:", value = "8540", color = Alternative)
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(110.dp)
                    .padding(4.dp)
            ) {
                CustomCircularProgressBar(
                    progress = 0.45f,
                    modifier = Modifier
                        .padding(8.dp), // So that both ProgressBars are visible
                    backgroundColor = Alternative.copy(alpha = 0.5f),
                    progressColor = Alternative,
                    strokeWidth = 8.dp
                )
                CustomCircularProgressBar(
                    progress = 0.66f,
                    modifier = Modifier,
                    backgroundColor = Secondary,
                    progressColor = Primary,
                    strokeWidth = 8.dp
                )
                Text(
                    text = "+13.5%",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
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
fun CustomCircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    progressColor: Color = Color.White,
    strokeWidth: Dp = 8.dp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(110.dp)
    ) {
        CircularProgressIndicator(
            progress = {
                1f
            },
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
        )

        CircularProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
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
fun CardTypesTile() {
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
                BarComponent(40.dp, TextGray, "Other")
                BarComponent(80.dp, Secondary, "Visa")
                BarComponent(65.dp, Primary, "Master")
            }
        }
    }
}

@Composable
fun BarComponent(
    height: Dp,
    color: Color = Primary,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(15.dp)
                .height(height)
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = TextWhite,
            fontSize = 12.sp
        )
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
                        label = days[index]
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionOutcomes() {
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BarComponent(80.dp, success, "Successful")
                BarComponent(30.dp, danger, "Canceled")
                BarComponent(15.dp, TextGray, "Rejected")
            }
        }
    }
}