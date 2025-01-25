package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.components.processing_components.ProcessingItem
import hr.foi.air.szokpt.core.processing.ProcessingRecord
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PreviousProcessingsView(navController: NavController) {
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
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Previous Processings",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

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
                    .padding(horizontal = 0.dp)
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
}
