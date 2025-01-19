package hr.foi.air.szokpt.ws.network

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionsPerDayResponse
import hr.foi.air.szokpt.ws.models.TransactionsSuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ReportsService {
    @GET("/reports/successful-transactions")
    fun getTransactionsSuccess(): Call<SuccessfulResponseBody<TransactionsSuccessResponse>>

    @GET("/reports/transactions-per-day")
    fun getTransactionsPerDay(@Header("Authorization") authHeader: String): Call<SuccessfulResponseBody<TransactionsPerDayResponse>>
}