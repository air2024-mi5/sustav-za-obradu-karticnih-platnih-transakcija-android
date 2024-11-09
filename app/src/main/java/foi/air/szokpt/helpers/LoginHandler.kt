package foi.air.szokpt.helpers

import foi.air.szokpt.network.LoginBody
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

        serviceCall.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful) {
                    onSuccessfulLogin(username)
                } else {
                    onFailure("Neuspješna prijava")
                }
                setAwaitingResponse(false)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure("Nešto je pošlo po zlu")
                setAwaitingResponse(false)
            }
        })
    }
}