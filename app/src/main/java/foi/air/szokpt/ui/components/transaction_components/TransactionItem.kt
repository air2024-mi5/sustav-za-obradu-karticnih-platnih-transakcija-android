package foi.air.szokpt.ui.components.transaction_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.models.Transaction
import foi.air.szokpt.ui.theme.BGLevelOne

/**
 * TransactionItem is an element which represents a single transaction.
 * @param transaction is an object that provides the transaction data needed to display
 */
@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(
                color = BGLevelOne,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TransactionIcon(transaction.type.first().toString())

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = transaction.type,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = transaction.description,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}