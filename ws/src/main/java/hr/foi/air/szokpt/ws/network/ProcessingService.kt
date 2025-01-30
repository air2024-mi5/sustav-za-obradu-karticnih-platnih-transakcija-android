package hr.foi.air.szokpt.ws.network

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.SelectedTransactions
import hr.foi.air.szokpt.ws.models.ProcessingPageResponse
import hr.foi.air.szokpt.ws.models.ProcessingResponse
import hr.foi.air.szokpt.ws.models.responses.SelectedTransaction
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

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
    ): Call<SuccessfulResponseBody<ProcessingResponse>>

    @PUT("/revert-last-processing")
    fun revertLastProcessing(
        @Header("Authorization") authHeader: String
    ): Call<SuccessfulResponseBody<Unit>>

    @GET("/processings")
    fun getAllProcessings(
        @Query("page") page: Int,
        @Header("Authorization") authHeader: String
    ): Call<SuccessfulResponseBody<ProcessingPageResponse>>
}