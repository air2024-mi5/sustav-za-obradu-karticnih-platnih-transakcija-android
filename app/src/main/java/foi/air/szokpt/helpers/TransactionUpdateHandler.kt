package foi.air.szokpt.helpers

import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.TransactionUpdateOutcomeListener
import hr.foi.air.szokpt.ws.models.responses.Transaction
import hr.foi.air.szokpt.ws.request_handlers.TransactionUpdateRequestHandler

class TransactionUpdateHandler {
    fun update(
        jwtToken: String,
        newTransactionData: Transaction,
        transactionUpdateOutcomeListener: TransactionUpdateOutcomeListener
    ) {
        val transactionUpdateRequestHandler = TransactionUpdateRequestHandler(jwtToken, newTransactionData)
        transactionUpdateRequestHandler.sendRequest(
            object : ResponseListener<Unit> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<Unit>) {
                    transactionUpdateOutcomeListener.onSuccessfulTransactionUpdate(response.message)
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    transactionUpdateOutcomeListener.onFailedTransactionUpdate(response.message)
                }

                override fun onNetworkFailure(t: Throwable) {
                    transactionUpdateOutcomeListener.onFailedTransactionUpdate(
                        "Please check your internet connection!"
                    )
                }
            }
        )
    }
}