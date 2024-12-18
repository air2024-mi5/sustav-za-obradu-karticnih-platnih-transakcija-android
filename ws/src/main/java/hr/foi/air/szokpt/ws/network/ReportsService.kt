package hr.foi.air.szokpt.ws.network

import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionsSuccessResponse
import retrofit2.Call
import retrofit2.http.GET

interface ReportsService {
    @GET("/reports/successful-transactions")
    fun getTransactionsSuccess(): Call<SuccessfulResponseBody<TransactionsSuccessResponse>>
}