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
}