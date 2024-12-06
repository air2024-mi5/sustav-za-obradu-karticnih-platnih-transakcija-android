package hr.foi.air.szokpt.ws.network

import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.core.login.LoginBody
import hr.foi.air.core.register.RegistrationBody
import hr.foi.air.szokpt.ws.models.LoginResponse
import hr.foi.air.szokpt.ws.models.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/login")
    fun login(@Body loginBody: LoginBody): Call<SuccessfulResponseBody<LoginResponse>>

    @POST("/register")
    fun register(
        @Header("Authorization") authHeader: String,
        @Body registrationBody: RegistrationBody
    ): Call<SuccessfulResponseBody<RegistrationResponse>>
}
