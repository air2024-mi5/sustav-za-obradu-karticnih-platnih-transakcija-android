package foi.air.szokpt.helpers

import NetworkService
import foi.air.szokpt.network.RegistrationBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationHandler(
    private val onSuccessful:(String) -> Unit,
    private val onFailure:(String) -> Unit,
    private val setAwaitingResponse:(Boolean) -> Unit
) {
    fun register(username: String, password: String, firstName: String, lastName: String, email: String, role: String) {
        setAwaitingResponse(true)

        val service = NetworkService.authenticationService
        val serviceCall = service.register(RegistrationBody(username, password, firstName, lastName, email, role))

        serviceCall.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        onSuccessful("Uspješna registracija!")
                        //val message = response.body()
                        //println("Uspjeh: $message")
                    } else {
                        onFailure("Neuspješna registracija!")
                        //println("Greska: ${response.errorBody()?.string()}")
                    }
                    setAwaitingResponse(false)
                }
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                onFailure("Došlo je do greške.")
            }
        })
    }
}