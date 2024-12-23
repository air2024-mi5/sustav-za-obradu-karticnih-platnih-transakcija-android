package hr.foi.air.szokpt.ws.network

import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.core.register.RegistrationBody
import hr.foi.air.szokpt.ws.models.RegistrationResponse
import hr.foi.air.szokpt.ws.models.responses.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersService {
    @POST("/register")
    fun register(
        @Header("Authorization") authHeader: String,
        @Body registrationBody: RegistrationBody
    ): Call<SuccessfulResponseBody<RegistrationResponse>>

    @GET("/users")
    fun getUsers(
        @Header("Authorization") authHeader: String,
    ): Call<SuccessfulResponseBody<User>>

    @PUT("/users/{id}")
    fun updateUser(
        @Header("Authorization") authHeader: String,
        @Path("id") userId: Int,
        @Body newUserAccountData: User
    ): Call<SuccessfulResponseBody<Unit>>
}