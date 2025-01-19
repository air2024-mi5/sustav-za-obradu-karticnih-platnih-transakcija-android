package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.SelectedTransactions
import retrofit2.Call

class AddSelectedTransactionsRequestHandler(
    private val jwtToken: String,
    private val guids: SelectedTransactions
) : TemplateRequestHandler<Unit>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Unit>> {
        val service = NetworkService.processingService
        return service.addSelectedTransactions("Bearer $jwtToken", guids)
    }
}