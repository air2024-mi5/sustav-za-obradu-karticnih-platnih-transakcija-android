package foi.air.szokpt.views.app

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.R
import foi.air.szokpt.models.TransactionFilter
import foi.air.szokpt.ui.components.filter_components.ModalBottomSheetFilter
import foi.air.szokpt.ui.components.interactible_components.BouncingFABDialogButton
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.pagination_components.Pagination
import foi.air.szokpt.ui.components.transaction_components.TransactionFilterView
import foi.air.szokpt.ui.components.transaction_components.TransactionItem
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.viewmodels.TransactionsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class FilterResults(
    val cardBrands: List<String>,
    val trxTypes: List<String>,
    val minAmount: String?,
    val maxAmount: String?,
    val afterDate: String?,
    val beforeDate: String?
)

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

    // Result of applied filters. Contains a list of filters that are applied
    var filterResults by remember { mutableStateOf<FilterResults?>(null) }

    // Result of applied filters. Contains a list of filters that are applied
    var filterResults by remember { mutableStateOf<TransactionFilter?>(null) }

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
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(3f)
        ) {
            transactionPage?.transactions?.forEach { transaction ->
                item {
                    TransactionItem(
                        transaction = transaction,
                        onClick = {
                            navController.navigate("transaction_details/${transaction.id}")
                        }
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
                baseIcon = ImageVector.vectorResource(id = R.drawable.rounded_filter_alt_24),
                expandedIcon = ImageVector.vectorResource(id = R.drawable.round_filter_fill_alt_24)
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
            filterResults = null
            isExpanded = false
        },
        filterOptionsContent = {
            TransactionFilter(
                initialFilter = filterResults,
                onApplyFilter = { results ->
                    filterResults = results
                    hasFilters = true
                    isShowingFilters = false
                    isExpanded = false
                }
            )
        }
    )
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
                baseIcon = ImageVector.vectorResource(id = R.drawable.rounded_filter_alt_24),
                expandedIcon = ImageVector.vectorResource(id = R.drawable.round_filter_fill_alt_24)
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
            filterResults = null
            isExpanded = false
        },
        filterOptionsContent = {
            TransactionFilterView(
                initialFilter = filterResults,
                onApplyFilter = { results ->
                    filterResults = results
                    hasFilters = true
                    isShowingFilters = false
                    isExpanded = false
                }
            )
        }
    )
}
