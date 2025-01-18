package hr.foi.air.szokpt.core.transactions

import java.util.UUID

data class SelectedTransactionGuids(
    val guids: List<UUID>,
)