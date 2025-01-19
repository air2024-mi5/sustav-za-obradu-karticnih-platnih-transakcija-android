package foi.air.szokpt.views.app

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.R
import foi.air.szokpt.ui.components.IconMessage
import foi.air.szokpt.ui.components.filter_components.ModalBottomSheetFilter
import foi.air.szokpt.ui.components.interactible_components.BouncingFABDialogButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.AddCandidatesButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.SelectAllTransactionsButton
import foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView.SelectTransactionsDialog
import foi.air.szokpt.ui.components.transaction_components.TransactionCandidateItem
import foi.air.szokpt.ui.components.transaction_components.TransactionFilterView
import foi.air.szokpt.viewmodels.TransactionsCandidatesViewModel
import kotlinx.coroutines.launch

@Composable
fun TransactionsCandidatesView(
    navController: NavController
) {
    val viewModel: TransactionsCandidatesViewModel = viewModel()
    val transactions by viewModel.transactions.observeAsState(emptyList())
    val selectedGuids by viewModel.selectedGuids.observeAsState()
    val filterResults by viewModel.transactionsFilter.observeAsState()
    val loading by viewModel.loading.observeAsState(true)
    val message by viewModel.message.observeAsState("")
    val showToast by viewModel.showToast.observeAsState(false)
    val toastMessage by viewModel.toastMessage.observeAsState("")

    val openAddCandidatesDialog = remember { mutableStateOf(false) }
    var isFilterExpanded by remember { mutableStateOf(false) }
    var hasFilters by remember { mutableStateOf(filterResults != null) }
    var isShowingFilters by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (transactions.isEmpty()) {
            viewModel.fetchSelectedTransactions()
            viewModel.fetchTransactionPage()
        }
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    if (showToast) {
        Toast.makeText(
            context,
            toastMessage,
            Toast.LENGTH_SHORT
        ).show()
        viewModel.resetToast()
    }

    val areAllSelected by remember(transactions, selectedGuids) {
        derivedStateOf {
            transactions.all { transaction ->
                transaction.guid in (selectedGuids?.transactions.orEmpty())
            }
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

        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .weight(3f),
        ) {
            if (transactions.isEmpty() && message.isEmpty()) {
                item {
                    IconMessage(
                        title = "No results found",
                        description = "No results were found that matched your request. Please change the filter or remove it.",
                        icon = Icons.Rounded.Search
                    )
                }
            } else if (transactions.isEmpty() && message.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = message,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            transactions.forEach { transaction ->
                item {
                    TransactionCandidateItem(
                        transaction = transaction,
                        isSelected = transaction.guid in (selectedGuids?.transactions.orEmpty()),
                        onSelectionChanged = { isSelectedNow ->
                            viewModel.updateSelectionStatus(transaction.guid, isSelectedNow)
                        },
                        onClick = {
                            navController.navigate("transaction_details/${transaction.guid}")
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

        ModalBottomSheetFilter(
            isVisible = isFilterExpanded,
            onDismiss = { isFilterExpanded = false },
            hasFilters = hasFilters,
            isShowingFilters = isShowingFilters,
            onShowFilterOptions = {
                isShowingFilters = true
            },
            onRemoveFilters = {
                hasFilters = false
                isFilterExpanded = false
                viewModel.setFilter(null)
                viewModel.fetchTransactionPage()
            },
            filterOptionsContent = {
                TransactionFilterView(
                    filter = viewModel.transactionsFilter.value,
                    onApplyFilter = { results ->
                        viewModel.setFilter(results)
                        hasFilters = true
                        isShowingFilters = false
                        isFilterExpanded = false
                        viewModel.fetchTransactionPage()
                    }
                )
            }
        )
    }
}
