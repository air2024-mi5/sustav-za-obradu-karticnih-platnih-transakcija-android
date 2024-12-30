package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.User
import retrofit2.Call

class AccountUpdateRequestHandler(
    private val jwtToken: String,
    private val newUserAccountData: User
) :
    TemplateRequestHandler<Unit>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Unit>> {
        val service = NetworkService.usersService
        return service.updateUser("Bearer $jwtToken", newUserAccountData.id, newUserAccountData)
    }
}