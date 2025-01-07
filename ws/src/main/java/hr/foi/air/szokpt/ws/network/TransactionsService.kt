package hr.foi.air.szokpt.ws.network

import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import hr.foi.air.szokpt.ws.models.responses.Transaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionsService {
    @GET("transactions")
    fun getTransactionPage(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("card_brand") cardBrands: List<String>?,
        @Query("trx_type") trxTypes: List<String>?,
        @Query("before") beforeDate: String?,
        @Query("after") afterDate: String?,
        @Query("amount_greater_than") minAmount: Int?,
        @Query("amount_less_than") maxAmount: Int?,
    ): Call<SuccessfulResponseBody<TransactionPageResponse>>

    @GET("transactions/{id}")
    fun getTransactionDetails(
        @Path("id") transactionId: Int
    ): Call<SuccessfulResponseBody<Transaction>>
}