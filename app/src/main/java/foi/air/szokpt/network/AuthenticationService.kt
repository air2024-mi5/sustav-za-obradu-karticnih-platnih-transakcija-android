package foi.air.szokpt.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/login")
    fun login(@Body loginBody: LoginBody): Call<String>
}
