package foi.air.szokpt.helpers

import hr.foi.air.szokpt.core.login.LoginBody
import hr.foi.air.szokpt.core.login.LoginOutcomeListener
import hr.foi.air.szokpt.core.login.LoginUserData
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.LoginResponse
import hr.foi.air.szokpt.ws.request_handlers.LoginRequestHandler


class LoginHandler() {
    fun login(loginBody: LoginBody, loginListener: LoginOutcomeListener) {
        val loginRequestHandler = LoginRequestHandler(loginBody)

        loginRequestHandler.sendRequest(
            object : ResponseListener<LoginResponse> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<LoginResponse>) {

                    val loginResponse = response.data!![0]

                    val payload = JwtUtils.decodeJwtPayload(loginResponse.token)

                    loginListener.onSuccessfulLogin(
                        LoginUserData(
                            payload.username,
                            payload.role,
                            loginResponse.token ?: ""
                        )
                    )
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    loginListener.onFailedLogin(response.message ?: "Unknown error occurred")
                }

                override fun onNetworkFailure(t: Throwable) {
                    loginListener.onFailedLogin("Could not connect to network.")
                }
            }
        )

    }
}