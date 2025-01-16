package hr.foi.air.szokpt.ws.models.responses

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Transaction(
    @SerializedName("guid") val guid: UUID,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("trx_type") val trxType: String,
    @SerializedName("installments_number") val installmentsNumber: Int,
    @SerializedName("installments_creditor") val installmentsCreditor: String,
    @SerializedName("card_brand") val cardBrand: String,
    @SerializedName("transaction_timestamp") val transactionTimestamp: String,
    @SerializedName("masked_pan") val maskedPan: String,
    @SerializedName("pin_used") val pinUsed: Boolean,
    @SerializedName("response_code") val responseCode: String,
    @SerializedName("approval_code") val approvalCode: String,
    @SerializedName("processed") val processed: Boolean
)