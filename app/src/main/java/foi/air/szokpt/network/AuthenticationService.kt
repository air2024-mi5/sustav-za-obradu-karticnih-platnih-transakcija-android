package foi.air.szokpt.network

import foi.air.szokpt.network.models.LoginBody
import foi.air.szokpt.network.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/login")
    fun login(@Body loginBody: LoginBody): Call<LoginResponse>
}
