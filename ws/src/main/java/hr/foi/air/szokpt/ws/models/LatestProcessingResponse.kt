package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName
import hr.foi.air.szokpt.core.processing.BatchRecord

data class LatestProcessingResponse(
    @SerializedName("status") val status: String,
    @SerializedName("scheduled_at") val scheduledAt: String,
    @SerializedName("processed_at") val processedAt: String,
    @SerializedName("batch_records") val batchRecords: List<BatchRecord>,
    @SerializedName("processed_transactions_count") val processedTransactionsCount: Int
)