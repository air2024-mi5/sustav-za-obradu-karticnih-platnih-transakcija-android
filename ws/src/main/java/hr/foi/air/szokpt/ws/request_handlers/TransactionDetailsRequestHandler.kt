package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.Transaction
import retrofit2.Call

class TransactionDetailsRequestHandler(
    private val transactionId: Int,
) : TemplateRequestHandler<Transaction>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Transaction>> {
        val service = NetworkService.transactionsService
        return service.getTransactionDetails(transactionId)
    }
}