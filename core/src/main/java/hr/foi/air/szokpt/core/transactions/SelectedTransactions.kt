package hr.foi.air.szokpt.core.transactions

import java.util.UUID

data class SelectedTransactions(
    val transactions: List<UUID>,
)