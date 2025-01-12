package hr.foi.air.szokpt.core.transactions

import java.util.UUID

data class TransactionData(
    val guid: UUID,
    val amount: Double,
    val currency: String,
    val trxType: String,
    val installmentsNumber: Int,
    val installmentsCreditor: String,
    val cardBrand: String,
    val transactionTimestamp: String,
    val maskedPan: String,
    val pinUsed: Boolean,
    val responseCode: String,
    val approvalCode: String,
    val processed: Boolean
)