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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.DatePickerField
import foi.air.szokpt.ui.components.InputNumberField
import foi.air.szokpt.ui.components.interactible_components.BouncingFABDialogButton
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.pagination_components.Pagination
import foi.air.szokpt.ui.components.transaction_components.TransactionItem
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.viewmodels.TransactionsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate


data class FilterResults(
    val cardBrands: List<String>,
    val trxTypes: List<String>,
    val minAmount: String?,
    val maxAmount: String?,
    val afterDate: LocalDate?,
    val beforeDate: LocalDate?
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
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isShowingFilters) {
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
                    item {
                        filterOptionsContent()
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionFilter(
    initialFilter: FilterResults?,
    onApplyFilter: (FilterResults) -> Unit
) {
    val cardBrands = listOf("Maestro", "Visa", "MasterCard", "Diners", "Discover")
    val trxTypeMap = mapOf(
        "sale" to "Sale",
        "refund" to "Refund",
        "void_sale" to "Void sale",
        "void_refund" to "Void refund",
        "reversal_sale" to "Reversal sale",
        "reversal_refund" to "Reversal refund"
    )

    val selectedCardBrands = remember { mutableStateListOf<String>().apply { initialFilter?.cardBrands?.let { addAll(it) } } }
    val selectedTrxTypes = remember { mutableStateListOf<String>().apply { initialFilter?.trxTypes?.let { addAll(it) } } }
    var minAmount by remember { mutableStateOf(initialFilter?.minAmount) }
    var maxAmount by remember { mutableStateOf(initialFilter?.maxAmount) }
    var selectedBeforeDate by remember { mutableStateOf(initialFilter?.beforeDate) }
    var selectedAfterDate by remember { mutableStateOf(initialFilter?.afterDate) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Transaction types",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    trxTypeMap.forEach { (key, displayValue) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (selectedTrxTypes.contains(key)) {
                                        selectedTrxTypes.remove(key)
                                    } else {
                                        selectedTrxTypes.add(key)
                                    }
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = selectedTrxTypes.contains(key),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) selectedTrxTypes.add(key) else selectedTrxTypes.remove(key)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Primary
                                )
                            )
                            Text(
                                text = displayValue,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Card brands",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    cardBrands.forEach { brand ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (selectedCardBrands.contains(brand)) {
                                        selectedCardBrands.remove(brand)
                                    } else {
                                        selectedCardBrands.add(brand)
                                    }
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = selectedCardBrands.contains(brand),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) selectedCardBrands.add(brand) else selectedCardBrands.remove(brand)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Primary
                                )
                            )
                            Text(
                                text = brand,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = "Date range",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            DatePickerField(
                onDateSelected = { date -> selectedAfterDate = date },
                label = "After date",
                initialDate = selectedAfterDate,
                maxWidth = 172.dp
            )
            DatePickerField(
                onDateSelected = { date -> selectedBeforeDate = date },
                label = "Before date",
                initialDate = selectedBeforeDate,
                maxWidth = 172.dp
            )
        }

        Text(
            text = "Amount value range",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            InputNumberField(
                label = "Min",
                value = minAmount ?: "",
                onValueChange = { minAmount = it.takeIf { it.isNotEmpty() } },
                width = 160.dp,
            )
            InputNumberField(
                label = "Max",
                value = maxAmount ?: "",
                onValueChange = { maxAmount = it.takeIf { it.isNotEmpty() } },
                width = 160.dp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            OutlineBouncingButton(
                inputText = "Apply filter",
                inputIcon = Icons.Rounded.Add,
                contentColor = success,
                borderColor = success,
                onClick = {
                    val results = FilterResults(
                        selectedCardBrands.toList(),
                        selectedTrxTypes.toList(),
                        minAmount,
                        maxAmount,
                        selectedAfterDate,
                        selectedBeforeDate
                    )
                    onApplyFilter(results)
                    println("FILTER RESULTS: $results")
                },
            )
        }
    }
}