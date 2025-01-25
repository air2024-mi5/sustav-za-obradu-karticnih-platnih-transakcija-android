package foi.air.szokpt.views.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.helpers.TransactionUtils
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelZeroHigh
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning
import foi.air.szokpt.viewmodels.ProcessingDetailsViewModel
import hr.foi.air.szokpt.core.processing.ProcessingRecord
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PreviousProcessingsView(navController: NavController) {
    val viewModel: ProcessingDetailsViewModel = viewModel()
    val context = LocalContext.current

    val mockProcessings = remember {
        listOf(
            ProcessingRecord(
                status = "COMPLETED",
                scheduledAt = LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ISO_DATE_TIME),
                processedAt = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
                batchRecords = emptyList(),
                processedTransactionsCount = 42
            ),
            ProcessingRecord(
                status = "COMPLETED",
                scheduledAt = LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ISO_DATE_TIME),
                processedAt = LocalDateTime.now().minusDays(3).format(DateTimeFormatter.ISO_DATE_TIME),
                batchRecords = emptyList(),
                processedTransactionsCount = 38
            ),
            ProcessingRecord(
                status = "REVERTED",
                scheduledAt = LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ISO_DATE_TIME),
                processedAt = LocalDateTime.now().minusDays(4).format(DateTimeFormatter.ISO_DATE_TIME),
                batchRecords = emptyList(),
                processedTransactionsCount = 18
            )
        )
    }

    var showRevertDialog by remember { mutableStateOf(false) }
    var selectedProcessing by remember { mutableStateOf<ProcessingRecord?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "Processing History",
                tint = Primary,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "Processing History",
                color = Primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Primary
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                items(mockProcessings) { processing ->
                    ProcessingItem(
                        processing = processing,
                    )
                }
            }
        }
    }

    if (showRevertDialog) {
        DialogComponent(
            dialogTitle = "Confirm Revert",
            dialogText = "Are you sure you want to revert this processing?\nThis action cannot be undone.",
            highlightColor = danger,
            titleColor = Color.White,
            onConfirmation = {
                selectedProcessing?.let { viewModel.revertLastProcessing() }
                showRevertDialog = false
            },
            onDismissRequest = { showRevertDialog = false },
        )
    }
}

@Composable
private fun ProcessingItem(
    processing: ProcessingRecord,
) {
    val statusColor = when (processing.status) {
        "COMPLETED" -> success
        "REVERTED" -> danger
        else -> warning
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = BGLevelOne, shape = RoundedCornerShape(10.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = processing.status,
                    color = statusColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${processing.processedTransactionsCount} transactions",
                    color = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))

            processing.scheduledAt?.let {
                Text(text = "Scheduled: ${formatDateTime(it)}")
            }

            processing.processedAt?.let {
                Text(text = "Processed: ${formatDateTime(it)}")
            }

            Text(
                text = "Batches: ${processing.batchRecords.size}",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}

private fun formatDateTime(isoString: String): String {
    return try {
        val dateTime = LocalDateTime.parse(isoString, DateTimeFormatter.ISO_DATE_TIME)
        dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"))
    } catch (e: Exception) {
        "Invalid date format"
    }
}