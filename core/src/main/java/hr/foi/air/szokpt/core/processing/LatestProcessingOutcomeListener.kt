package hr.foi.air.szokpt.core.processing

interface LatestProcessingOutcomeListener {
    fun onSuccessfulLatestProcessingFetch(
        status: String,
        scheduledAt: String,
        processedAt: String,
        batchRecords: List<BatchRecord>,
        processedTransactionsCount: Int
    )

    fun onFailedLatestProcessingFetch(
        failureMessage: String
    )
}