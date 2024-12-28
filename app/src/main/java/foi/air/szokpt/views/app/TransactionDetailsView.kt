package foi.air.szokpt.views.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.helpers.TransactionUtils
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.transaction_components.TransactionDetailRow
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import hr.foi.air.szokpt.ws.models.responses.Transaction

@Composable
fun TransactionDetailsView(
    navController: NavController,
    transaction: Transaction
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Transaction Details",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                TileSegment(
                    tileSizeMode = TileSizeMode.WRAP_CONTENT,
                    innerPadding = 8.dp,
                    outerMargin = 0.dp,
                    minWidth = 250.dp,
                    minHeight = 90.dp,
                    color = Color.Transparent
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(BGLevelOne, Primary),
                                    startY = 0f,
                                    endY = 1600f
                                )
                            )
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Info,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = BGLevelOne,
                                            shape = RoundedCornerShape(30.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Transaction #${transaction.id}",
                                        color = Primary,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    val currencySymbol =
                                        TransactionUtils.getCurrencySymbol(transaction.currency)
                                    Text(
                                        text = currencySymbol,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    Text(
                                        text = "${transaction.amount}",
                                        color = TextWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                TileSegment(
                    tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
                    innerPadding = 8.dp,
                    outerMargin = 8.dp,
                    minWidth = 250.dp,
                    color = BGLevelOne
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        TransactionDetailRow("Date", transaction.transactionTimestamp)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row() {
                                Text(
                                    text = "Card",
                                    color = TextWhite.copy(alpha = 0.7f),
                                    fontSize = 16.sp
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = transaction.maskedPan,
                                    color = TextWhite,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 5.dp)
                                )
                                val cardBrandDrawable =
                                    TransactionUtils.getCardBrandDrawable(transaction.cardBrand)
                                Image(
                                    painter = painterResource(id = cardBrandDrawable),
                                    contentDescription = "Card Brand",
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                        }
                        val transactionType =
                            TransactionUtils.getTransactionTypeDisplay(transaction.trxType)
                        TransactionDetailRow(
                            "Type",
                            transactionType ?: transaction.trxType
                        )
                        if (transaction.installmentsNumber > 0) {
                            TransactionDetailRow(
                                "Installments",
                                transaction.installmentsNumber.toString()
                            )
                            TransactionDetailRow("Creditor", transaction.installmentsCreditor)
                        }
                        TransactionDetailRow("PIN Used", if (transaction.pinUsed) "Yes" else "No")
                        TransactionDetailRow(
                            "Status",
                            if (transaction.processed) "Processed" else "Pending"
                        )
                        TransactionDetailRow("Response Code", transaction.responseCode)
                    }
                }
            }
        }
    }
}
