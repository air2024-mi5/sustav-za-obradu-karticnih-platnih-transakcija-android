package hr.foi.air.szokpt.core.transactions

data class TransactionData(
    val id: Int,
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
    val processed: Boolean
)