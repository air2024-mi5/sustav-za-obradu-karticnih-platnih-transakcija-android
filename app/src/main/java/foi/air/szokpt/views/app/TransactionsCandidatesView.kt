package foi.air.szokpt.views.app

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.components.filter_components.ModalBottomSheetFilter
import foi.air.szokpt.ui.components.interactible_components.BouncingFABDialogButton
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.transaction_components.TransactionCandidateItem
import foi.air.szokpt.ui.components.transaction_components.TransactionFilterView
import foi.air.szokpt.ui.theme.DarkGreen
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning
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
            viewModel.initializeFilter()
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
                modifier = Modifier
                    .size(24.dp),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, Primary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    transactions.let { transactions ->
                        val pageGuids = transactions.map { it.guid }.toSet()
                        viewModel.toggleSelectAllTransactions(pageGuids)
                    }
                },
            ) {
                if (allTransactionsSelected) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "",
                        tint = Primary,
                    )
                }
            }
        }

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
            if (!loading && transactions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (message.isEmpty()) {
                            IconMessage(
                                title = "No transactions found!",
                                description = "",
                                icon = Icons.Rounded.Search
                            )
                        } else {
                            Text(
                                text = message,
                                color = Color.Red,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            if (transactions.isNotEmpty()) {
                items(transactions) { transaction ->
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

            OutlineBouncingButton(
                inputText = "Confirm selection",
                inputIcon = Icons.Rounded.CheckCircle,
                contentColor = if (!selectedGuids?.transactions.isNullOrEmpty()) success else DarkGreen,
                borderColor = if (!selectedGuids?.transactions.isNullOrEmpty()) success else DarkGreen,
                onClick = {
                    val isEnabled = selectedGuids?.transactions?.isNotEmpty() == true
                    if (isEnabled) {
                        openAddCandidatesDialog.value = true
                    }
                }
            )
        }
        if (openAddCandidatesDialog.value) {
            DialogComponent(
                dialogTitle = "Confirm Selection",
                dialogText = "Are you sure you want to confirm the selected transactions for daily processing?\n",
                highlightColor = warning,
                titleColor = TextWhite,
                onConfirmation = {
                    viewModel.addSelectedTransactions()
                    openAddCandidatesDialog.value = false
                },
                onDismissRequest = { openAddCandidatesDialog.value = false },
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
                viewModel.initializeFilter()
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
