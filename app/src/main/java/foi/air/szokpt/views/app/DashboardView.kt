package foi.air.szokpt.views.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.theme.Alternative
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success

@Composable
fun DashboardView(navController: NavController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        item(span = { GridItemSpan(2) }) {
            AllTransactionsTile()
        }
        item {
            ValueTile()
        }
        item {
            TileSegment(
                tileSizeMode = TileSizeMode.WRAP_CONTENT,
                minWidth = 200.dp,
                minHeight = 200.dp,
                color = BGLevelOne,
                content = {
                    Text(
                        text = "Item 2",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            )
        }
        item(span = { GridItemSpan(2) }) {
            TransactionsListTile()
        }
        item(span = { GridItemSpan(2) }) {
            TileSegment(
                tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
                // For spanning 2 rows
                modifier = Modifier.aspectRatio(1.5f),
                color = BGLevelOne,
                content = {
                    Text(
                        text = "Item 4 Span 1.5 row 2 col",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            )
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

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
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

            TransactionItem(initial = "F", cardType = "MasterCard", transactionLabel = "FOI")
            Spacer(modifier = Modifier.height(2.dp))
            TransactionItem(initial = "S", cardType = "Visa", transactionLabel = "Sjever")
            Spacer(modifier = Modifier.height(2.dp))
            TransactionItem(initial = "F", cardType = "MasterCard", transactionLabel = "FER")
        }
    }
}

@Composable
fun TransactionItem(
    initial: String,
    cardType: String,
    transactionLabel: String,
    backgroundColor: Color = BGLevelTwo,
    iconColor: Color = Secondary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconColor, shape = RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initial,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = cardType,
                color = TextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = transactionLabel,
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

