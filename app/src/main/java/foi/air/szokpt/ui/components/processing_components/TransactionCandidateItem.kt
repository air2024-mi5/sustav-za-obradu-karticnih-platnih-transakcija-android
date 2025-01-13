package foi.air.szokpt.ui.components.processing_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foi.air.szokpt.helpers.TransactionUtils
import foi.air.szokpt.ui.theme.BGLevelZeroHigh
import hr.foi.air.szokpt.ws.models.responses.Transaction

@Composable
fun TransactionCandidateItem(
    transaction: Transaction,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = BGLevelZeroHigh, shape = RoundedCornerShape(8.dp))
            .drawBehind {
                val borderColor =
                    if (transaction.responseCode == "00" || transaction.responseCode == "11") {
                        Color.Green
                    } else {
                        Color.Red
                    }
                drawRect(
                    color = borderColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(4.dp.toPx(), size.height)
                )
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.transactionTimestamp,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                val currencySymbol = TransactionUtils.getCurrencySymbol(transaction.currency)
                val currencyColor = TransactionUtils.getCurrencyColor(transaction.currency)
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
                text = "Pan: ${transaction.maskedPan}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelectionChanged(it) }
        )
    }
}
