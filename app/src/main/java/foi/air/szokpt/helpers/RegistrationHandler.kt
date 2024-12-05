package foi.air.szokpt.helpers

import hr.foi.air.core.login.RegistrationBody
import hr.foi.air.core.network.ResponseListener
import hr.foi.air.core.network.models.ErrorResponseBody
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.core.register.RegistrationOutcomeListener
import hr.foi.air.szokpt.ws.models.RegistrationResponse
import hr.foi.air.szokpt.ws.request_handlers.RegistrationRequestHandler

class RegistrationHandler() {
    fun register(registrationBody: RegistrationBody, registrationListener: RegistrationOutcomeListener) {
        val registrationRequestHandler = RegistrationRequestHandler(registrationBody)

        registrationRequestHandler.sendRequest(
            object : ResponseListener<RegistrationResponse> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<RegistrationResponse>) {
                    registrationListener.onSuccessfulRegistration(response.message)
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    registrationListener.onFailedRegistration(response.message)
                }

                override fun onNetworkFailure(t: Throwable) {
                    registrationListener.onFailedRegistration("Could not connect to network.")
                }
            }
        )
    }
}