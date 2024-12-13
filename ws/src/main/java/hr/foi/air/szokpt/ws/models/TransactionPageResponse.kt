package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName
import hr.foi.air.szokpt.ws.models.responses.Transaction

data class TransactionPageResponse(
    @SerializedName("transactions") val transactions: List<Transaction>,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("totalPages") val totalPages: Int
)