package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.processing_components.TransactionCandidateItem
import hr.foi.air.szokpt.ws.models.responses.Transaction
import java.util.UUID

@Composable
fun TransactionsCandidatesView(navController: NavController) {
    val mockTransactions = listOf(
        Transaction(
            guid = UUID.randomUUID(),
            maskedPan = "165650****0054",
            responseCode = "00",
            cardBrand = "Visa",
            currency = "840",
            amount = 50.0,
            transactionTimestamp = "12/01/2025 10:03:17",
            trxType = "Purchase",
            installmentsNumber = 1,
            installmentsCreditor = "",
            pinUsed = false,
            approvalCode = "00",
            processed = false
        ),

        Transaction(
            guid = UUID.randomUUID(),
            maskedPan = "765230****0054",
            responseCode = "00",
            cardBrand = "Visa",
            currency = "978",
            amount = 100.0,
            transactionTimestamp = "12/01/2025 09:03:17",
            trxType = "Purchase",
            installmentsNumber = 1,
            installmentsCreditor = "",
            pinUsed = false,
            approvalCode = "00",
            processed = false
        ),
    )

    var selectedTransactionIds by remember { mutableStateOf(setOf<UUID>()) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(mockTransactions) { transaction ->
            val isSelected = selectedTransactionIds.contains(transaction.guid)

            TransactionCandidateItem(
                transaction = transaction,
                isSelected = isSelected,
                onSelectionChanged = { isSelectedNow ->
                    selectedTransactionIds = if (isSelectedNow) {
                        selectedTransactionIds + transaction.guid
                    } else {
                        selectedTransactionIds - transaction.guid
                    }
                }
            )
        }
    }
}