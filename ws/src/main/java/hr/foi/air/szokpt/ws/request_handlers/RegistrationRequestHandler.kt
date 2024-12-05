package hr.foi.air.szokpt.ws.request_handlers

import hr.foi.air.core.login.RegistrationBody
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.RegistrationResponse
import retrofit2.Call

class RegistrationRequestHandler(private val requestBody: RegistrationBody) :
    TemplateRequestHandler<RegistrationResponse>() {
        override fun getServiceCall(): Call<SuccessfulResponseBody<RegistrationResponse>> {
            val service = NetworkService.authenticationService
            return service.register(requestBody)
        }
}