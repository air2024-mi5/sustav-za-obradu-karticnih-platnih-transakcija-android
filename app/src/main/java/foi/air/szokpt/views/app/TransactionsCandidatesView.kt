package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.processing_components.TransactionCandidateItem
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
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
    val areAllSelected by remember {
        derivedStateOf { selectedTransactionIds.size == mockTransactions.size }
    }

    val iconTintColor by remember {
        derivedStateOf { if (areAllSelected) Primary else Color.LightGray }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    selectedTransactionIds = mockTransactions.map { it.guid }.toSet()
                },
                modifier = Modifier
                    .size(32.dp),
                shape = RectangleShape,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Select All",
                    tint = iconTintColor,
                )
            }
            Text(
                text = "Select All",
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.padding(8.dp)
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
}