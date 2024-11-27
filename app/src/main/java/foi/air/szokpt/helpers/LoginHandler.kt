package foi.air.szokpt.helpers

import NetworkService
import android.util.Log
import com.google.gson.Gson
import foi.air.szokpt.context.Auth
import foi.air.szokpt.network.models.LoginBody
import foi.air.szokpt.network.models.LoginResponse
import foi.air.szokpt.network.models.LoginUserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginHandler(
    private val onSuccessfulLogin:(String) -> Unit,
    private val onFailure:(String) -> Unit,
    private val setAwaitingResponse:(Boolean) -> Unit
) {
    fun login(username: String, password: String) {
        setAwaitingResponse(true)

        val service = NetworkService.authenticationService
        val serviceCall = service.login(LoginBody(username, password))

        serviceCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody == null) {
                        onFailure("Neuspješna prijava")
                    }else{
                        val payload = JwtUtils.decodeJwtPayload(responseBody.token)

                        Auth.logedInUserData = LoginUserData(
                            payload.username,
                            payload.role,
                            responseBody.token?:""
                        )

                        onSuccessfulLogin(username)
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error occurred"
                    val errorMessage = try {
                        Gson().fromJson(errorBody, LoginResponse::class.java)?.message
                            ?: "Invalid response from server"
                    } catch (e: Exception) {
                        "Error parsing server response"
                    }
                    onFailure(errorMessage)
                }
                setAwaitingResponse(false)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailure("Nešto je pošlo po zlu")
                setAwaitingResponse(false)
            }
        })
    }
}