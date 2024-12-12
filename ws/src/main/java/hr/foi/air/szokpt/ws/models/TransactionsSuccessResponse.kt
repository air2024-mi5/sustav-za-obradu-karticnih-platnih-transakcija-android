package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName

data class TransactionsSuccessResponse(
    @SerializedName("totalTransactions") val totalTransactions: Int,
    @SerializedName("successfulTransactions") val successfulTransactions: Int,
    @SerializedName("rejectedTransactions") val rejectedTransactions: Int,
    @SerializedName("canceledTransactions") val canceledTransactions: Int
)