package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.core.register.RegistrationBody
import hr.foi.air.szokpt.ws.models.RegistrationResponse
import retrofit2.Call

class RegistrationRequestHandler(
    private val jwtToken: String,
    private val requestBody: RegistrationBody
) :
    TemplateRequestHandler<RegistrationResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<RegistrationResponse>> {
        val service = NetworkService.usersService
        return service.register("Bearer $jwtToken", requestBody)
    }
}