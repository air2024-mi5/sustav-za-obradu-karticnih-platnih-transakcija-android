package hr.foi.air.szokpt.ws.network

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.SelectedTransaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProcessingService {
    @GET("/selected-transactions")
    fun getSelectedTransactions(
        @Header("Authorization") authHeader: String
    ): Call<SuccessfulResponseBody<SelectedTransaction>>
}