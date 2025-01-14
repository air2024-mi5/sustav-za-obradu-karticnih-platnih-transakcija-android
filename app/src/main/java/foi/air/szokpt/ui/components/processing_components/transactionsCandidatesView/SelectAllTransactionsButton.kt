package foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.viewmodels.TransactionsCandidatesViewModel
import hr.foi.air.szokpt.ws.models.responses.Transaction

@Composable
fun SelectAllTransactionsButton(
    transactions: List<Transaction>?,
    viewModel: TransactionsCandidatesViewModel,
    iconTintColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Select all",
            color = Primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 8.dp)
        )

        Button(
            onClick = {
                transactions?.let { transactions ->
                    val pageGuids = transactions.map { it.guid }.toSet()
                    viewModel.toggleSelectAllTransactions(pageGuids)
                }
            },
            modifier = Modifier
                .size(24.dp),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Primary),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "",
                tint = iconTintColor,
            )
        }
    }
}
