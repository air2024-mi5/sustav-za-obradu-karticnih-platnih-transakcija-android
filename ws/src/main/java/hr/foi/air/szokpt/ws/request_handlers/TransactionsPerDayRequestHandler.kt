package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionsPerDayResponse
import retrofit2.Call

class TransactionsPerDayRequestHandler(private val jwtToken: String) :
    TemplateRequestHandler<TransactionsPerDayResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<TransactionsPerDayResponse>> {
        val service = NetworkService.reportsService
        return service.getTransactionsPerDay("Bearer $jwtToken")
    }
}