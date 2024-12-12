package hr.foi.air.szokpt.ws.models.responses

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id") val id: Int,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("trxType") val trxType: String,
    @SerializedName("installmentsNumber") val installmentsNumber: Int,
    @SerializedName("installmentsCreditor") val installmentsCreditor: String,
    @SerializedName("cardBrand") val cardBrand: String,
    @SerializedName("transactionTimestamp") val transactionTimestamp: String,
    @SerializedName("maskedPan") val maskedPan: String,
    @SerializedName("pinUsed") val pinUsed: Boolean,
    @SerializedName("responseCode") val responseCode: String,
    @SerializedName("processed") val processed: Boolean
)