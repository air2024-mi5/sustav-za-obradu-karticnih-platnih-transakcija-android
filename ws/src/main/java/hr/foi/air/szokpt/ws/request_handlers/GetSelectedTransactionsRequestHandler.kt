package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.SelectedTransaction
import retrofit2.Call

class GetSelectedTransactionsRequestHandler(
    private val jwtToken: String,
) : TemplateRequestHandler<SelectedTransaction>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<SelectedTransaction>> {
        val service = NetworkService.processingService
        return service.getSelectedTransactions("Bearer $jwtToken")
    }
}