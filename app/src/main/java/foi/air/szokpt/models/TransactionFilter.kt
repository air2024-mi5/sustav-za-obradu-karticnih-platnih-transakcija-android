package foi.air.szokpt.models

data class TransactionFilter(
    val cardBrands: List<String>,
    val trxTypes: List<String>,
    val minAmount: String?,
    val maxAmount: String?,
    val afterDate: String?,
    val beforeDate: String?
)