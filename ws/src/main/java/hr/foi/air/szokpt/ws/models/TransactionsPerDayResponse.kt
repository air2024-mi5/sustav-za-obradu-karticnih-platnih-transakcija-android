package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName


data class TransactionsPerDayResponse(
    @SerializedName("totalTransactions") val totalTransactions: Int,
    @SerializedName("transactionsPerDay") val transactionsPerDay: Map<String, Int>
)