package foi.air.szokpt.views.app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.interactible_components.BouncingFABDialogButton
import foi.air.szokpt.ui.components.pagination_components.Pagination
import foi.air.szokpt.ui.components.transaction_components.TransactionItem
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.viewmodels.TransactionsViewModel
import kotlinx.coroutines.launch

@Composable
fun TransactionsView(navController: NavController) {
    val viewModel: TransactionsViewModel = viewModel()
    val transactionPage by viewModel.transactionPage.observeAsState()
    val currentPage by viewModel.currentPage.observeAsState()
    val totalPages by viewModel.totalPages.observeAsState()
    val loading by viewModel.loading.observeAsState(true)

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var isExpanded by remember { mutableStateOf(false) }
    var hasFilters by remember { mutableStateOf(false) }
    var isShowingFilters by remember { mutableStateOf(false) }

    LaunchedEffect(currentPage) {
        if (transactionPage == null) {
            viewModel.fetchTransactionPage(1)
        }
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Transactions",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(3f)
        ) {
            transactionPage?.transactions?.forEach { transaction ->
                item {
                    TransactionItem(transaction = transaction)
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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Pagination(
                    currentPage = currentPage,
                    totalPages = totalPages,
                    onPageSelected = { page ->
                        viewModel.fetchTransactionPage(page)
                        coroutineScope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                )
            }
            BouncingFABDialogButton(
                isExpanded = isExpanded,
                onToggle = { isExpanded = !isExpanded },
            )
        }
    }
    ModalBottomSheetFilter(
        isVisible = isExpanded,
        onDismiss = { isExpanded = false },
        hasFilters = hasFilters,
        isShowingFilters = isShowingFilters,
        onShowFilterOptions = {
            isShowingFilters = true
        },
        onRemoveFilters = {
            hasFilters = false
            isExpanded = false
        },
        filterOptionsContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Filter Options",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Example filter options
                Text(
                    text = "Option 1",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            hasFilters = true
                            isShowingFilters = false
                        }
                )
                Text(
                    text = "Option 2",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            hasFilters = true
                            isShowingFilters = false
                        }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetFilter(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    hasFilters: Boolean,
    isShowingFilters: Boolean,
    onShowFilterOptions: () -> Unit,
    onRemoveFilters: () -> Unit,
    filterOptionsContent: @Composable () -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = BGLevelOne,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isShowingFilters) {
                    // Main actions: Apply/Change Filter and Remove Filters
                    item {
                        Text(
                            text = if (hasFilters) "Change Current Filter" else "Apply New Filter",
                            fontSize = 16.sp,
                            color = success,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(9.dp)
                                .clickable {
                                    onShowFilterOptions()
                                }
                        )
                    }

                    if (hasFilters) {
                        item {
                            Text(
                                text = "Remove Filters",
                                fontSize = 16.sp,
                                color = danger,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(9.dp)
                                    .clickable {
                                        onRemoveFilters()
                                    }
                            )
                        }
                    }
                } else {
                    // Show filter options
                    item {
                        filterOptionsContent()
                    }
                }
            }
        }
    }
}