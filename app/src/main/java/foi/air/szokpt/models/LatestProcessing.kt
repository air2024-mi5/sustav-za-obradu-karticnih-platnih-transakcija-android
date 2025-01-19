package foi.air.szokpt.models

import java.time.LocalDateTime

data class LatestProcessing(
    val id: Int,
    val date: LocalDateTime,
    val status: String,
    val processedTransactions: Int
)