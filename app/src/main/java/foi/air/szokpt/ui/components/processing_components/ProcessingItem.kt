package foi.air.szokpt.ui.components.processing_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning
import hr.foi.air.szokpt.core.processing.ProcessingRecord
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ProcessingItem(
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