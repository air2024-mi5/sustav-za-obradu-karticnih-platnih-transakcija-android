package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.pagination_components.Pagination
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.SelectAllTransactionsButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.SelectCandidatesButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.TransactionCandidateItem
import foi.air.szokpt.viewmodels.TransactionsCandidatesViewModel
import kotlinx.coroutines.launch

@Composable
fun TransactionsCandidatesView(
    navController: NavController
) {
    val viewModel: TransactionsCandidatesViewModel = viewModel()
    val transactions by viewModel.transactions.observeAsState()
    val currentPage by viewModel.currentPage.observeAsState()
    val totalPages by viewModel.totalPages.observeAsState()
    val selectedGuids by viewModel.selectedGuids.observeAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentPage) {
        if (transactions!!.isEmpty()) {
            viewModel.fetchSelectedTransactions()
            viewModel.fetchTransactionPage(1)
        }
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    val areAllSelected by remember {
        derivedStateOf {
            transactions?.map { it.guid }?.all { it in selectedGuids.orEmpty() }
                ?: false
        }
    }

    val allTransactionsSelected by remember {
        derivedStateOf { if (!areAllSelected) false else true }
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
                        isSelected = transaction.guid in selectedGuids.orEmpty(),
                        onSelectionChanged = { isSelectedNow ->
                            viewModel.updateSelectionStatus(transaction.guid, isSelectedNow)
                        }
                    )
                }
            }
        }

        Pagination(
            currentPage = currentPage,
            totalPages = totalPages,
            onPageSelected = { page ->
                viewModel.fetchTransactionPage(page)
                coroutineScope.launch {
                    listState.scrollToItem(0)
                }
            })

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            SelectCandidatesButton()
        }
    }
}