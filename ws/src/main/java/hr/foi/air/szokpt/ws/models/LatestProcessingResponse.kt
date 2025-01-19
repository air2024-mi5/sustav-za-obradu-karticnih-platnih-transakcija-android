package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName
import hr.foi.air.szokpt.ws.models.responses.BatchRecord
import java.time.LocalDateTime

data class LatestProcessingResponse(
    @SerializedName("status") val status: String,
    @SerializedName("scheduled_at") val scheduledAt: LocalDateTime,
    @SerializedName("processed_at") val processedAt: LocalDateTime,
    @SerializedName("batch_records") val batchRecords: List<BatchRecord>,
    @SerializedName("processed_transactions_count") val processedTransactionsCount: Int
)