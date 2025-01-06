package hr.foi.air.szokpt.core.transactions

data class TransactionFilter(
    val cardBrands: List<String>,
    val trxTypes: List<String>,
    val minAmount: Int?,
    val maxAmount: Int?,
    val afterDate: String?,
    val beforeDate: String?
)