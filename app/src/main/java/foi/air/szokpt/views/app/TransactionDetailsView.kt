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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.transaction_components.TransactionDetailRow
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.utils.TransactionUtils
import foi.air.szokpt.viewmodels.TransactionDetailsViewModel
import foi.air.szokpt.views.ROUTE_EDIT_TRANSACTION
import java.math.RoundingMode
import java.util.UUID

@Composable
fun TransactionDetailsView(
    navController: NavController,
    transactionGuid: UUID,
    viewModel: TransactionDetailsViewModel = viewModel()
) {
    val transaction by viewModel.transactionData.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    viewModel.fetchTransactionDetails(transactionGuid = transactionGuid)

    Column(modifier = Modifier.fillMaxSize()) {
        if (transaction == null) {
            errorMessage?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = it,
                        color = TextWhite,
                        fontSize = 18.sp,
                    )
                }
            }
        } else {
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
                                            text = "Transaction #${transaction!!.guid}",
                                            color = Primary,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        val currencySymbol =
                                            TransactionUtils.getCurrencySymbol(transaction!!.currency)
                                        Text(
                                            text = "$currencySymbol ${transaction!!.amount}",
                                            color = TextWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                        if (!transaction!!.processed) {
                                            OutlineBouncingButton(
                                                onClick = {
                                                    navController.navigate("${ROUTE_EDIT_TRANSACTION}/${transactionGuid}")
                                                },
                                                contentColor = TextWhite,
                                                borderColor = TextWhite,
                                                inputIcon = Icons.Rounded.Edit,
                                                inputText = "Edit",
                                                modifier = Modifier
                                            )
                                        }
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
                            TransactionDetailRow("Date", transaction!!.transactionTimestamp)
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
                                        text = transaction!!.maskedPan,
                                        color = TextWhite,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(horizontal = 5.dp)
                                    )
                                    val cardBrandDrawable =
                                        TransactionUtils.getCardBrandDrawable(transaction!!.cardBrand)
                                    Image(
                                        painter = painterResource(id = cardBrandDrawable),
                                        contentDescription = "Card Brand",
                                        modifier = Modifier.size(45.dp)
                                    )
                                }
                            }
                            val transactionType =
                                TransactionUtils.getTransactionTypeDisplay(transaction!!.trxType)
                            TransactionDetailRow(
                                "Type",
                                transactionType ?: transaction!!.trxType
                            )
                            if (transaction!!.installmentsNumber > 0) {
                                val monthlyAmount =
                                    (transaction!!.amount / transaction!!.installmentsNumber).toBigDecimal()
                                        .setScale(2, RoundingMode.HALF_UP)

                                TransactionDetailRow(
                                    "Payment Method",
                                    "Installments - ${transaction!!.installmentsNumber} mths."
                                )
                                TransactionDetailRow(
                                    "Monthly Amount",
                                    "${TransactionUtils.getCurrencySymbol(transaction!!.currency)} $monthlyAmount"
                                )
                                TransactionDetailRow(
                                    "Total Amount",
                                    "${
                                        TransactionUtils.getCurrencySymbol(
                                            transaction!!.currency
                                        )
                                    } ${transaction!!.amount}"
                                )
                                TransactionDetailRow("Creditor", transaction!!.installmentsCreditor)
                            } else {
                                TransactionDetailRow(
                                    "Payment Method",
                                    "One-time payment"
                                )
                            }
                            TransactionDetailRow(
                                "PIN Used",
                                if (transaction!!.pinUsed) "Yes" else "No"
                            )
                            TransactionDetailRow(
                                "Status",
                                if (transaction!!.processed) "Processed" else "Not processed"
                            )
                            TransactionDetailRow("Response Code", transaction!!.responseCode)
                        }
                    }
                }
            }
        }
    }
}
