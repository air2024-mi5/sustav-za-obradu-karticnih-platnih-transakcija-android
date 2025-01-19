package hr.foi.air.szokpt.ws.models.responses

import com.google.gson.annotations.SerializedName

data class BatchRecordDto(
    @SerializedName("batch_header") val batchHeader: String,
    @SerializedName("terminal_parameter_record") val terminalParameterRecord: String,
    @SerializedName("batch_trailer") val batchTrailer: String,
    @SerializedName("transaction_records") val transactionRecords: List<String>,
)