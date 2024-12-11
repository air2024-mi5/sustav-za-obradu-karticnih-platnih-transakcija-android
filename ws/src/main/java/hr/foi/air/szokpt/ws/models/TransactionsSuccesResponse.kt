package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName

data class TransactionsSuccessResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: TransactionsSuccess
)

data class TransactionsSuccess(
    @SerializedName("totalTransactions") val totalTransactions: Int,
    @SerializedName("successfulTransactions") val successfulTransactions: Int,
    @SerializedName("rejected") val rejectedTransactions: Int,
    @SerializedName("canceled") val canceledTransactions: Int
)