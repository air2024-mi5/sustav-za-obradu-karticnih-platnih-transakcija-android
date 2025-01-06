package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionsSuccessResponse
import retrofit2.Call

class TransactionsSuccessRequestHandler : TemplateRequestHandler<TransactionsSuccessResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<TransactionsSuccessResponse>> {
        val service = NetworkService.reportsService
        return service.getTransactionsSuccess()
    }
}