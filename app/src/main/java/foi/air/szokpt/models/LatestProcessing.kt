package foi.air.szokpt.models

import hr.foi.air.szokpt.core.processing.BatchRecord

data class LatestProcessing(
    val status: String = "",
    val scheduledAt: String? = "",
    val processedAt: String? = "",
    val batchRecords: List<BatchRecord> = emptyList(),
    val processedTransactionsCount: Int = 0,
)