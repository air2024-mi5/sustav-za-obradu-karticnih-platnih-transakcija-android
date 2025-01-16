package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.Transaction
import retrofit2.Call
import java.util.UUID

class TransactionDetailsRequestHandler(
    private val jwtToken: String,
    private val transactionGuid: UUID,
) : TemplateRequestHandler<Transaction>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Transaction>> {
        val service = NetworkService.transactionsService
        return service.getTransactionDetails( "Bearer $jwtToken", transactionGuid)
    }
}