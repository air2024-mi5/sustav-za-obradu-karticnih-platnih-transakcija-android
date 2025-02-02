package hr.foi.air.szokpt.ws.network

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.CardBrandsStatisticsResponse
import hr.foi.air.szokpt.ws.models.TransactionsPerDayResponse
import hr.foi.air.szokpt.ws.models.TransactionsSuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ReportsService {
    @GET("/reports/successful-transactions")
    fun getTransactionsSuccess(@Header("Authorization") authHeader: String):
            Call<SuccessfulResponseBody<TransactionsSuccessResponse>>

    @GET("/reports/card-brands")
    fun getCardBrandsStatistics(@Header("Authorization") authHeader: String):
            Call<SuccessfulResponseBody<CardBrandsStatisticsResponse>>

    @GET("/reports/transactions-per-day")
    fun getTransactionsPerDay(@Header("Authorization") authHeader: String):
            Call<SuccessfulResponseBody<TransactionsPerDayResponse>>
}