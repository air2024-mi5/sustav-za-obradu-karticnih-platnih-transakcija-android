package hr.foi.air.szokpt.ws.request_handlers

import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.core.login.LoginBody
import hr.foi.air.szokpt.ws.models.LoginResponse
import retrofit2.Call

class LoginRequestHandler(private val requestBody: LoginBody) :
    TemplateRequestHandler<LoginResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<LoginResponse>> {
        val service = NetworkService.authenticationService
        return service.login(requestBody)
    }
}