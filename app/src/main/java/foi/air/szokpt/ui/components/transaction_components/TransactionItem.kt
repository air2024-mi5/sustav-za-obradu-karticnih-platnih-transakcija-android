package foi.air.szokpt.ui.components.transaction_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import foi.air.szokpt.helpers.TransactionUtils
import foi.air.szokpt.ui.theme.BGLevelZeroHigh
import hr.foi.air.szokpt.ws.models.responses.Transaction

/**
 * TransactionItem is an element which represents a single transaction.
 * @param transaction is an object that provides the transaction data needed to display
 */
@Composable
fun TransactionItem(transaction: Transaction, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = BGLevelZeroHigh, shape = RoundedCornerShape(8.dp))
            .drawBehind {
                val strokeWidth = 4.dp.toPx()
                val borderColor =
                    if (transaction.responseCode == "00" || transaction.responseCode == "11") {
                        Color.Green
                    } else {
                        Color.Red
                    }
                drawRect(
                    color = borderColor,
                    topLeft = Offset(size.width - strokeWidth, 0f),
                    size = Size(strokeWidth, size.height)
                )
            }
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            val cardBrandDrawable =
                TransactionUtils.getCardBrandDrawable(transaction.cardBrand)
            Image(
                painter = painterResource(id = cardBrandDrawable),
                contentDescription = "Card Brand",
                modifier = Modifier
                    .size(60.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = transaction.maskedPan,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "Installments: ${transaction.installmentsNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier.weight(1.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val currencySymbol =
                    TransactionUtils.getCurrencySymbol(transaction.currency)
                val currencyColor =
                    TransactionUtils.getCurrencyColor(transaction.currency)
                Text(
                    text = currencySymbol,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = currencyColor,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = "${transaction.amount}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Text(
                text = transaction.transactionTimestamp,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
            val transactionType =
                TransactionUtils.getTransactionTypeDisplay(transaction.trxType)
            Text(
                text = transactionType ?: transaction.trxType,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}
