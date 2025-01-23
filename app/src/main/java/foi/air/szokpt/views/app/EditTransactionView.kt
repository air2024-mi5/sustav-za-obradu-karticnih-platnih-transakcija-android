package foi.air.szokpt.views.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.R
import foi.air.szokpt.helpers.TransactionUpdateHandler
import foi.air.szokpt.helpers.TransactionUtils
import foi.air.szokpt.ui.components.DatePickerField
import foi.air.szokpt.ui.components.InputTimePicker
import foi.air.szokpt.ui.components.StyledTextField
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning
import foi.air.szokpt.viewmodels.TransactionViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun EditTransactionView(
    navController: NavController,
    transactionGuid: UUID,
    viewModel: TransactionViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initializeTransactionData(transactionGuid)
    }

    val currentTransaction by viewModel.currentTransactionData.observeAsState()
    val storedTransaction by viewModel.storedTransactionData.observeAsState()
    val errorMessage by viewModel.message.observeAsState()


    var showDialog by remember { mutableStateOf(false) }
    var updatedTransactionTimestamp by remember { mutableStateOf("") }
    var selectedNewAmount by remember { mutableStateOf<Double?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Transaction Edit",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        if (currentTransaction == null) {
            errorMessage?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = it,
                        color = TextWhite,
                        fontSize = 18.sp,
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                item {
                    TileSegment(
                        tileSizeMode = TileSizeMode.WRAP_CONTENT,
                        innerPadding = 8.dp,
                        outerMargin = 0.dp,
                        minWidth = 250.dp,
                        minHeight = 90.dp,
                        color = Color.Transparent
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(BGLevelOne, success),
                                        startY = 0f,
                                        endY = 4000f
                                    )
                                )
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = BGLevelOne,
                                                shape = RoundedCornerShape(30.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = "#${storedTransaction!!.guid}",
                                            color = TextWhite.copy(alpha = 0.85f),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        val currencySymbol =
                                            TransactionUtils.getCurrencySymbol(storedTransaction!!.currency)
                                        Text(
                                            text = "$currencySymbol ${storedTransaction!!.amount}",
                                            color = TextWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                        OutlineBouncingButton(
                                            onClick = {
                                                showDialog = true
                                            },
                                            contentColor = success,
                                            borderColor = success,
                                            inputIcon = Icons.Rounded.Refresh,
                                            inputText = "Save",
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    TileSegment(
                        tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
                        innerPadding = 8.dp,
                        outerMargin = 8.dp,
                        minWidth = 250.dp,
                        minHeight = 100.dp,
                        color = BGLevelOne
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            val currencySymbol =
                                TransactionUtils.getCurrencySymbol(currentTransaction!!.currency)
                            StyledTextField(
                                label = "New $currencySymbol Amount",
                                value = selectedNewAmount?.toString()
                                    ?: currentTransaction!!.amount.toString(),
                                onValueChange = { newValue ->
                                    newValue.toDoubleOrNull()?.let { newAmount ->
                                        selectedNewAmount = newAmount
                                        viewModel.updateAmount(newAmount)
                                    }
                                },
                                isPasswordField = false,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            val originalTimestamp = currentTransaction!!.transactionTimestamp
                            updatedTransactionTimestamp = originalTimestamp

                            val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

                            val initialDate =
                                LocalDate.parse(originalTimestamp.split(" ")[0], dateFormatter)
                            val initialTime =
                                LocalTime.parse(originalTimestamp.split(" ")[1], timeFormatter)

                            var selectedDate by remember { mutableStateOf<LocalDate?>(initialDate) }
                            var selectedTime by remember { mutableStateOf<LocalTime?>(initialTime) }
                            var showTimePicker by remember { mutableStateOf(false) }

                            fun updateTransactionTimestamp() {
                                val date = selectedDate?.format(dateFormatter) ?: ""
                                val time = selectedTime?.format(timeFormatter) ?: ""
                                updatedTransactionTimestamp = "$date $time"
                                viewModel.updateTimestamp(updatedTransactionTimestamp)
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                DatePickerField(
                                    onDateSelected = { date ->
                                        selectedDate = date
                                        updateTransactionTimestamp()
                                        showTimePicker = true
                                    },
                                    label = "Choose Date & Time",
                                    initialDate = LocalDate.parse(initialDate.toString()),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp)
                                )
                                if (selectedDate != null && selectedTime != null) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                    ) {
                                        Text(
                                            text = selectedTime!!.format(
                                                DateTimeFormatter.ofPattern(
                                                    "HH:mm:ss"
                                                )
                                            ),
                                            color = TextWhite,
                                            fontSize = 16.sp,
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_schedule_24),
                                            contentDescription = "Time Icon",
                                            modifier = Modifier.size(32.dp),
                                            tint = Primary
                                        )
                                    }
                                }
                            }
                            if (showTimePicker) {
                                InputTimePicker(
                                    onConfirm = { hour, minute ->
                                        selectedTime = LocalTime.of(hour, minute)
                                        showTimePicker = false
                                    },
                                    onDismiss = {
                                        showTimePicker = false
                                    }
                                )
                            }
                        }
                    }
                    if (!errorMessage.isNullOrEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 80.dp)
                                .padding(bottom = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = errorMessage!!,
                                color = warning,
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
            }
        }
        if (showDialog) {
            DialogComponent(
                onDismissRequest = { showDialog = false },
                onConfirmation = {

                    val isValid = viewModel.validateData(currentTransaction!!)
                    if (isValid) {
                        viewModel.updateTransactionData(
                            transactionUpdateHandler = TransactionUpdateHandler(),
                            currentTransaction!!
                        )

                        showDialog = false
                        navController.navigate("transaction_details/${storedTransaction!!.guid}")
                    } else {
                        showDialog = false
                        viewModel.setMessage("Please check your input!")
                    }

                },
                dialogTitle = "Confirm Changes",
                dialogText = "Are you sure you want to change ${storedTransaction!!.guid}?",
                iconTop = Icons.Rounded.Refresh,
                highlightColor = success,
                confirmationText = "Save",
                dismissText = "Cancel",
                iconConfirm = Icons.Rounded.Check,
                iconDismiss = Icons.Rounded.Close,
                titleColor = Color.White
            )
        }

    }
}