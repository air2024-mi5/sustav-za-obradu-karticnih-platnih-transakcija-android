package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import retrofit2.Call

class GetTransactionsPageRequestHandler(
    private val jwtToken: String,
    private val page: Int
) :
    TemplateRequestHandler<TransactionPageResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<TransactionPageResponse>> {
        val service = NetworkService.transactionsService
        return service.getTransactionPage("Bearer $jwtToken", page = page)
    }
}