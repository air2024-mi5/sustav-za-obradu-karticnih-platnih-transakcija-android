package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.TransactionFilter
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import retrofit2.Call

class GetTransactionsPageRequestHandler(
    private val jwtToken: String,
    private val page: Int,
    private val filter: TransactionFilter?
) :
    TemplateRequestHandler<TransactionPageResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<TransactionPageResponse>> {
        val service = NetworkService.transactionsService
        return service.getTransactionPage(
            "Bearer $jwtToken",
            page = page,
            cardBrands = filter?.cardBrands,
            trxTypes = filter?.trxTypes,
            minAmount = filter?.minAmount,
            maxAmount = filter?.maxAmount,
            beforeDate = filter?.beforeDate,
            afterDate = filter?.afterDate,
            processed = filter?.processed,
        )
    }
}