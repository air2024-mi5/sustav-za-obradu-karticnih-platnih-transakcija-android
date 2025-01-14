package hr.foi.air.szokpt.ws.models.responses

import java.util.UUID

data class SelectedTransactions(
    val transactions: List<UUID>,
)