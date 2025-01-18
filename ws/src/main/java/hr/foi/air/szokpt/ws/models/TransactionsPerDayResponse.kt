package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class TransactionsPerDayResponse(
    @SerializedName("totalTransactions") val totalTransactions: Int,
    @SerializedName("transactionsPerDay") val transactionsPerDay: Map<Timestamp, Int>
)