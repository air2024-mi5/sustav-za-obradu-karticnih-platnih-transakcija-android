package foi.air.szokpt.views.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.SelectCandidatesButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.TransactionCandidateItem
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextBlack
import foi.air.szokpt.viewmodels.TransactionsCandidatesViewModel
import hr.foi.air.szokpt.ws.models.responses.Transaction
import kotlinx.coroutines.launch

@Composable
fun TransactionsCandidatesView(
    navController: NavController
) {
    val viewModel: TransactionsCandidatesViewModel = viewModel()
    val transactionPage by viewModel.transactionPage.observeAsState()
    val currentPage by viewModel.currentPage.observeAsState()
    val totalPages by viewModel.totalPages.observeAsState()
    val selectedGuids by viewModel.selectedGuids.observeAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentPage) {
        if (transactionPage == null) {
            viewModel.fetchTransactionPage(1)
        }
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    val areAllSelected by remember {
        derivedStateOf {
            transactionPage?.transactions?.map { it.guid }?.all { it in selectedGuids.orEmpty() }
                ?: false
        }
    }

    val iconTintColor by remember {
        derivedStateOf { if (!areAllSelected) TextBlack else Primary }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        SelectAllButton(
            transactionPage?.transactions,
            viewModel,
            iconTintColor
        )

        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            transactionPage?.transactions?.forEach { transaction ->
                item {
                    TransactionCandidateItem(
                        transaction = transaction,
                        isSelected = transaction.guid in selectedGuids.orEmpty(),
                        onSelectionChanged = { isSelectedNow ->
                            viewModel.updateSelectionStatus(transaction.guid, isSelectedNow)
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            SelectCandidatesButton()
        }
    }
}

@Composable
fun SelectAllButton(
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
