package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import retrofit2.Call

class TransactionsRequestHandler(
    private val page: Int
) :
    TemplateRequestHandler<TransactionPageResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<TransactionPageResponse>> {
        val service = NetworkService.transactionsService
        return service.getTransactions(page = page)
    }
}