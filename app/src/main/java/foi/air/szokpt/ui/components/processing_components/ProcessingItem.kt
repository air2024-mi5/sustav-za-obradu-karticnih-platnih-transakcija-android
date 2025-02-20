package foi.air.szokpt.ui.components.processing_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning
import foi.air.szokpt.utils.DateFormatter
import hr.foi.air.szokpt.core.processing.ProcessingRecord

@Composable
fun ProcessingItem(
    processing: ProcessingRecord,
    onClick: () -> Unit
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
            .padding(8.dp)
            .clickable { onClick() },
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
                Text(
                    text = "Scheduled: ${DateFormatter.format(it)}",
                    color = TextWhite
                )
            }

            processing.processedAt?.let {
                Text(
                    text = "Processed: ${DateFormatter.format(it)}",
                    color = TextWhite
                )
            }

            Text(
                text = "Batches: ${processing.batchRecords.size}",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}