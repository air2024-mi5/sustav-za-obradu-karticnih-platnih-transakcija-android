package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.R
import foi.air.szokpt.ui.components.interactible_components.BouncingFABDialogButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.AddCandidatesButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.SelectAllTransactionsButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.SelectTransactionsDialog
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.TransactionCandidateItem
import foi.air.szokpt.viewmodels.TransactionsCandidatesViewModel
import kotlinx.coroutines.launch

@Composable
fun TransactionsCandidatesView(
    navController: NavController
) {
    val viewModel: TransactionsCandidatesViewModel = viewModel()
    val transactions by viewModel.transactions.observeAsState()
    val selectedGuids by viewModel.selectedGuids.observeAsState()

    val openAddCandidatesDialog = remember { mutableStateOf(false) }
    var isFilterExpanded by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (transactions.isNullOrEmpty()) {
            viewModel.fetchSelectedTransactions()
            viewModel.fetchTransactionPage()
        }
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    val areAllSelected by remember(transactions, selectedGuids) {
        derivedStateOf {
            transactions?.all { transaction ->
                transaction.guid in (selectedGuids?.transactions.orEmpty())
            } ?: false
        }
    }

    val allTransactionsSelected by remember {
        derivedStateOf { areAllSelected }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        SelectAllTransactionsButton(
            transactions,
            viewModel,
            allTransactionsSelected
        )

        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            transactions?.forEach { transaction ->
                item {
                    TransactionCandidateItem(
                        transaction = transaction,
                        isSelected = transaction.guid in (selectedGuids?.transactions.orEmpty()),
                        onSelectionChanged = { isSelectedNow ->
                            viewModel.updateSelectionStatus(transaction.guid, isSelectedNow)
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 8.dp, 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            BouncingFABDialogButton(
                isExpanded = isFilterExpanded,
                onToggle = { isFilterExpanded = !isFilterExpanded },
                baseIcon = ImageVector.vectorResource(id = R.drawable.rounded_filter_alt_24),
                expandedIcon = ImageVector.vectorResource(id = R.drawable.round_filter_fill_alt_24)
            )
            Spacer(modifier = Modifier.weight(1f))
            AddCandidatesButton(
                selectedGuids = selectedGuids?.transactions.orEmpty(),
                openAddCandidatesDialog
            )
        }
        if (openAddCandidatesDialog.value) {
            SelectTransactionsDialog(
                openAddCandidatesDialog = openAddCandidatesDialog,
                onConfirm = {
                    viewModel.addSelectedTransactions()
                }
            )
        }
    }
}