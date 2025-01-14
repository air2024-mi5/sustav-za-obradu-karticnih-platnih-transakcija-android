package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.SelectedTransactions
import retrofit2.Call
import java.util.UUID

class AddSelectedTransactions(
    private val jwtToken: String,
    private val guids: List<UUID>
) : TemplateRequestHandler<Unit>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Unit>> {
        val service = NetworkService.processingService
        val selectedTransactions = SelectedTransactions(transactions = guids)
        return service.addSelectedTransactions("Bearer $jwtToken", selectedTransactions)
    }
}