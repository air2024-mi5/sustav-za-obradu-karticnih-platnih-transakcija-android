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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.models.TransactionFilter
import foi.air.szokpt.ui.components.DatePickerField
import foi.air.szokpt.ui.components.InputNumberField
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.success
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TransactionFilterView(
    initialFilter: TransactionFilter?,
    onApplyFilter: (TransactionFilter) -> Unit
) {
    val context = LocalContext.current

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
    fun isValidAmountRange(min: String?, max: String?): Boolean {
        val minValue = min?.toDoubleOrNull()
        val maxValue = max?.toDoubleOrNull()
        return minValue == null || maxValue == null || minValue <= maxValue
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var selectedAfterDate by remember {
        mutableStateOf(
            initialFilter?.afterDate?.let { LocalDate.parse(it, dateFormatter) }
        )
    }
    var selectedBeforeDate by remember {
        mutableStateOf(
            initialFilter?.beforeDate?.let { LocalDate.parse(it, dateFormatter) }
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
        }
    }
    fun validateAndSetBeforeDate(date: LocalDate) {
        if (selectedAfterDate != null && date.isBefore(selectedAfterDate)) {
            Toast.makeText(
                context,
                "'Before Date' cannot be earlier than the 'After Date'",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            selectedBeforeDate = date
        }
    }

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
                onDateSelected = { date -> validateAndSetAfterDate(date) },
                label = "After date",
                initialDate = selectedAfterDate,
                maxWidth = 172.dp
            )
            DatePickerField(
                onDateSelected = { date -> validateAndSetBeforeDate(date) },
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
                onValueChange = {
                    maxAmount = it.takeIf { it.isNotEmpty() }
                    Log.i("valuechange", "test")
                },
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

                    if (isValidAmountRange(minAmount, maxAmount)) {
                        val results = TransactionFilter(
                            cardBrands = selectedCardBrands.toList(),
                            trxTypes = selectedTrxTypes.toList(),
                            minAmount = minAmount?.takeIf { it.isNotEmpty() },
                            maxAmount = maxAmount?.takeIf { it.isNotEmpty() },
                            afterDate = selectedAfterDate?.format(dateFormatter),
                            beforeDate = selectedBeforeDate?.format(dateFormatter)
                        )
                        onApplyFilter(results)
                        // Theese are the results!
                        Log.i("filtervalues", results.toString())
                    } else {
                        println("Invalid amount range: min=$minAmount, max=$maxAmount")
                        Toast.makeText(context, "Invalid amount range: Min must be less than or equal to Max", Toast.LENGTH_SHORT).show()
                    }
                },
            )
        }
    }
}