package hr.foi.air.szokpt.core.processing

import java.time.LocalDateTime

interface LatestProcessingOutcomeListener {
    fun onSuccessfulLatestProcessingFetch(
        status: String,
        scheduledAt: LocalDateTime,
        processedAt: LocalDateTime,
        batchRecords: List<BatchRecord>,
        processedTransactionsCount: Int
    )

    fun onFailedLatestProcessingFetch(
        failureMessage: String
    )
}