package hr.foi.air.szokpt.core.processing

data class ProcessingRecord(
    val status: String = "",
    val scheduledAt: String? = "",
    val processedAt: String? = "",
    val batchRecords: List<BatchRecord> = emptyList(),
    val processedTransactionsCount: Int = 0,
)