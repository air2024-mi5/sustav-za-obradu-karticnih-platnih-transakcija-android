package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.User
import retrofit2.Call

class GetAccountsRequestHandler(
    private val jwtToken: String,
) : TemplateRequestHandler<User>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<User>> {
        val service = NetworkService.usersService
        return service.getUsers("Bearer $jwtToken")
    }
}