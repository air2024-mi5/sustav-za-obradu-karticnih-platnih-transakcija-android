package foi.air.szokpt.ui.components.transaction_components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.R
import foi.air.szokpt.ui.components.DatePickerField
import foi.air.szokpt.ui.components.InputNumberField
import foi.air.szokpt.ui.components.InputTimePicker
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.viewmodels.TransactionsViewModel
import hr.foi.air.szokpt.core.transactions.TransactionFilter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar

@Composable
fun TransactionFilterView(
    initialFilter: TransactionFilter?,
    viewModel: TransactionsViewModel,
    onApplyFilter: (TransactionFilter) -> Unit
) {
    val context = LocalContext.current

    // Observe the existing filter from the ViewModel
    val transactionsFilter = viewModel.transactionsFilter.observeAsState().value

    var showAfterTimePicker by remember { mutableStateOf(false) }
    var showBeforeTimePicker by remember { mutableStateOf(false) }

    var selectedAfterDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var selectedBeforeDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val cardBrands = listOf("Maestro", "Visa", "MasterCard", "Diners", "Discover")
    val trxTypeMap = mapOf(
        "sale" to "Sale",
        "refund" to "Refund",
        "void_sale" to "Void sale",
        "void_refund" to "Void refund",
        "reversal_sale" to "Reversal sale",
        "reversal_refund" to "Reversal refund"
    )

    val selectedCardBrands =
        remember { mutableStateListOf<String>().apply { initialFilter?.cardBrands?.let { addAll(it) } } }
    val selectedTrxTypes =
        remember { mutableStateListOf<String>().apply { initialFilter?.trxTypes?.let { addAll(it) } } }

    var minAmount by remember { mutableStateOf(initialFilter?.minAmount) }
    var maxAmount by remember { mutableStateOf(initialFilter?.maxAmount) }

    fun isValidAmountRange(min: Int?, max: Int?): Boolean {
        return min == null || max == null || min <= max
    }

    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val dateOnlyFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    var selectedAfterDate by remember {
        mutableStateOf(
            transactionsFilter?.afterDate?.let {
                try {
                    LocalDateTime.parse(it, dateTimeFormatter).toLocalDate()
                } catch (e: DateTimeParseException) {
                    LocalDate.parse(it, dateOnlyFormatter)
                }
            }
        )
    }

    var selectedBeforeDate by remember {
        mutableStateOf(
            transactionsFilter?.beforeDate?.let {
                try {
                    LocalDateTime.parse(it, dateTimeFormatter).toLocalDate()
                } catch (e: DateTimeParseException) {
                    LocalDate.parse(it, dateOnlyFormatter)
                }
            }
        )
    }

    var selectedAfterTime by remember {
        mutableStateOf(
            transactionsFilter?.afterDate?.let {
                try {
                    LocalDateTime.parse(it, dateTimeFormatter).toLocalTime()
                } catch (e: DateTimeParseException) {
                    null
                }
            }
        )
    }

    var selectedBeforeTime by remember {
        mutableStateOf(
            transactionsFilter?.beforeDate?.let {
                try {
                    LocalDateTime.parse(it, dateTimeFormatter).toLocalTime()
                } catch (e: DateTimeParseException) {
                    null
                }
            }
        )
    }

    fun validateAndSetAfterDate(date: LocalDate) {
        if (selectedBeforeDate != null && date.isAfter(selectedBeforeDate)) {
            Toast.makeText(
                context,
                "'After Date' cannot be later than the 'Before Date'.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            selectedAfterDate = date
            showAfterTimePicker = true
        }
    }

    fun validateAndSetBeforeDate(date: LocalDate) {
        if (selectedAfterDate != null && date.isBefore(selectedAfterDate)) {
            Toast.makeText(
                context,
                "'Before Date' cannot be earlier than the 'After Date'.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            selectedBeforeDate = date
            showBeforeTimePicker = true
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
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
                                    if (isChecked) selectedTrxTypes.add(key) else selectedTrxTypes.remove(
                                        key
                                    )
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
                    verticalArrangement = Arrangement.spacedBy(2.dp)
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
                                    if (isChecked) selectedCardBrands.add(brand) else selectedCardBrands.remove(
                                        brand
                                    )
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
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column {
                DatePickerField(
                    onDateSelected = { date -> validateAndSetAfterDate(date) },
                    label = "After date",
                    initialDate = selectedAfterDate,
                    maxWidth = 172.dp
                )
                selectedAfterDateTime?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .align(Alignment.End)
                    ) {
                        Text(
                            text = it.format(timeFormatter),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.round_schedule_24),
                            contentDescription = "Time Icon",
                            modifier = Modifier.size(16.dp),
                            tint = Primary
                        )
                    }
                }
                // After Time Picker
                if (showAfterTimePicker) {
                    InputTimePicker(
                        onConfirm = { hour, minute ->
                            selectedAfterTime = LocalTime.of(hour, minute)
                            showAfterTimePicker = false
                            if (selectedAfterDate != null) {
                                selectedAfterDateTime = LocalDateTime.of(selectedAfterDate, selectedAfterTime)
                            }
                        },
                        onDismiss = {
                            showAfterTimePicker = false
                        }
                    )
                }
            }
            Column {
                Log.i("filter-time", "Before Date Value: $selectedBeforeDate")
                DatePickerField(
                    onDateSelected = { date -> validateAndSetBeforeDate(date) },
                    label = "Before date",
                    initialDate = selectedBeforeDate,
                    maxWidth = 172.dp
                )
                selectedBeforeDateTime?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .align(Alignment.End)
                    ) {
                        Text(
                            text = it.format(timeFormatter),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.round_schedule_24),
                            contentDescription = "Time Icon",
                            modifier = Modifier.size(16.dp),
                            tint = Primary
                        )
                    }
                }
                if (showBeforeTimePicker) {
                    InputTimePicker(
                        onConfirm = { hour, minute ->
                            selectedBeforeTime = LocalTime.of(hour, minute, 0)
                            showBeforeTimePicker = false
                            if (selectedBeforeDate != null) {
                                selectedBeforeDateTime = LocalDateTime.of(
                                    selectedBeforeDate,
                                    LocalTime.of(hour, minute)
                                )
                            }
                        },
                        onDismiss = {
                            // Set time to 00:00 when InputTimePicker is dismissed
                            selectedBeforeDateTime = LocalDateTime.of(
                                selectedBeforeDate ?: LocalDate.now(),
                                LocalTime.MIDNIGHT
                            )
                            showBeforeTimePicker = false
                        }
                    )
                }
            }
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
                value = minAmount?.toString() ?: "",
                onValueChange = { minAmount = it.takeIf { it.isNotEmpty() }?.toIntOrNull() },
                width = 160.dp,
            )
            InputNumberField(
                label = "Max",
                value = maxAmount?.toString() ?: "",
                onValueChange = {
                    maxAmount = it.takeIf { it.isNotEmpty() }?.toIntOrNull()
                },
                width = 160.dp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            OutlineBouncingButton(
                inputText = "Apply filter",
                inputIcon = Icons.Rounded.Add,
                contentColor = success,
                borderColor = success,
                onClick = {
                    if (isValidAmountRange(minAmount, maxAmount)) {
                        val results = TransactionFilter(
                            cardBrands = selectedCardBrands.toList(),
                            trxTypes = selectedTrxTypes.toList(),
                            minAmount = minAmount,
                            maxAmount = maxAmount,
                            afterDate = formatTime(selectedAfterDate, selectedAfterTime),
                            beforeDate = formatTime(selectedBeforeDate, selectedBeforeTime)
                        )
                        onApplyFilter(results)
                    } else {
                        Toast.makeText(
                            context,
                            "Invalid amount range: Min must be less than or equal to Max",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
            )
        }
    }
}

fun formatTime(date: LocalDate?, time: LocalTime?): String? {
    val tempDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    return if (date != null && time != null) {
        LocalDateTime.of(date, time).format(tempDateFormatter)
    } else null
}
