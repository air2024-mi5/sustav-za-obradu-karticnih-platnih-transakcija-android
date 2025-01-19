package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName
import hr.foi.air.szokpt.ws.models.responses.BatchRecordDto
import java.time.LocalDateTime

data class LatestProcessingResultResponse(
    @SerializedName("status") val status: String,
    @SerializedName("scheduled_at") val scheduledAt: LocalDateTime,
    @SerializedName("processed_at") val processedAt: LocalDateTime,
    @SerializedName("batch_records") val batchRecords: List<BatchRecordDto>,
)