package foi.air.szokpt.helpers

import android.util.Log
import hr.foi.air.szokpt.core.register.RegistrationBody
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.register.RegistrationOutcomeListener
import hr.foi.air.szokpt.ws.models.RegistrationResponse
import hr.foi.air.szokpt.ws.request_handlers.RegistrationRequestHandler

class RegistrationHandler() {
    fun register(jwtToken: String, registrationBody: RegistrationBody, registrationListener: RegistrationOutcomeListener) {
        val registrationRequestHandler = RegistrationRequestHandler(jwtToken, registrationBody)
        registrationRequestHandler.sendRequest(
            object : ResponseListener<RegistrationResponse> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<RegistrationResponse>) {
                    registrationListener.onSuccessfulRegistration(response.message)
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    registrationListener.onFailedRegistration(response.message  ?: "Registration error occurred")
                }

                override fun onNetworkFailure(t: Throwable) {
                    registrationListener.onFailedRegistration(t.message ?: "An unknown network error occurred.")
                }
            }
        )
    }
}