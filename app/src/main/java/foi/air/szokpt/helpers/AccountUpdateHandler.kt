package foi.air.szokpt.helpers

import hr.foi.air.core.accounts.AccountUpdateOutcomeListener
import hr.foi.air.core.network.ResponseListener
import hr.foi.air.core.network.models.ErrorResponseBody
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.User
import hr.foi.air.szokpt.ws.request_handlers.AccountUpdateRequestHandler

class AccountUpdateHandler() {
    fun update(
        jwtToken: String,
        newUserData: User,
        accountUpdateOutcomeListener: AccountUpdateOutcomeListener
    ) {
        val accountUpdateRequestHandler = AccountUpdateRequestHandler(jwtToken, newUserData)
        accountUpdateRequestHandler.sendRequest(
            object : ResponseListener<Unit> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<Unit>) {
                    accountUpdateOutcomeListener.onSuccessfulAccountUpdate(response.message)
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    accountUpdateOutcomeListener.onFailedAccountUpdate(
                        response.message
                    )
                }

                override fun onNetworkFailure(t: Throwable) {
                    accountUpdateOutcomeListener.onFailedAccountUpdate(
                        t.message ?: "An unknown network error occurred."
                    )
                }
            }
        )
    }

}