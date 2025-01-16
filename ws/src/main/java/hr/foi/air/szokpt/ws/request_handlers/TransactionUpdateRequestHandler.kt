package hr.foi.air.szokpt.ws.request_handlers

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.Transaction
import retrofit2.Call

class TransactionUpdateRequestHandler(
    private val jwtToken: String,
    private val newTransactionData: Transaction
) :
    TemplateRequestHandler<Unit>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Unit>> {
        val service = NetworkService.transactionsService
        return service.updateTransaction(
            "Bearer $jwtToken",
            newTransactionData.guid,
            newTransactionData
        )
    }
}