package hr.foi.air.szokpt.ws.network

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.SelectedTransactions
import hr.foi.air.szokpt.ws.models.LatestProcessingResponse
import hr.foi.air.szokpt.ws.models.responses.SelectedTransaction
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProcessingService {
    @GET("/selected-transactions")
    fun getSelectedTransactions(
        @Header("Authorization") authHeader: String
    ): Call<SuccessfulResponseBody<SelectedTransaction>>

    @POST("selected-transactions")
    fun addSelectedTransactions(
        @Header("Authorization") authHeader: String,
        @Body body: SelectedTransactions
    ): Call<SuccessfulResponseBody<Unit>>

    @GET("/last-processing")
    fun getLatestProcessingResult(
        @Header("Authorization") authHeader: String
    ): Call<SuccessfulResponseBody<LatestProcessingResponse>>
}