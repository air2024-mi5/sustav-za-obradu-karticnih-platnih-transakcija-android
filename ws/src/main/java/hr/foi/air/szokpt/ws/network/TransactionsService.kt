package hr.foi.air.szokpt.ws.network

import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionsService {
    @GET("transactions")
    fun getTransactions(
        @Query("page") page: Int,
    ): Call<SuccessfulResponseBody<TransactionPageResponse>>
}