package foi.air.szokpt.models

import hr.foi.air.szokpt.core.processing.BatchRecord
import java.time.LocalDateTime

data class LatestProcessing(
    val status: String = "",
    val scheduledAt: LocalDateTime? = null,
    val processedAt: LocalDateTime? = null,
    val batchRecords: List<BatchRecord> = emptyList(),
    val processedTransactionsCount: Int = 0,
)