package foi.air.szokpt.handlers

import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.TransactionsSuccessOutcomeListener
import hr.foi.air.szokpt.ws.models.TransactionsSuccessResponse
import hr.foi.air.szokpt.ws.request_handlers.TransactionsSuccessRequestHandler

class TransactionsSuccessHandler {
    fun getTransactionsSuccess(
        jwtToken: String,
        transactionsListener: TransactionsSuccessOutcomeListener
    ) {
        val transactionsRequestHandler = TransactionsSuccessRequestHandler(jwtToken)

        transactionsRequestHandler.sendRequest(
            object : ResponseListener<TransactionsSuccessResponse> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionsSuccessResponse>) {
                    val transactionsArray = response.data

                    if (!transactionsArray.isNullOrEmpty()) {
                        val transactionsData = transactionsArray[0]
                        transactionsListener.onSuccessfulTransactionsSuccessFetch(
                            totalTransactions = transactionsData.totalTransactions,
                            successfulTransactions = transactionsData.successfulTransactions,
                            rejectedTransactions = transactionsData.rejectedTransactions,
                            canceledTransactions = transactionsData.canceledTransactions
                        )
                    } else {
                        transactionsListener.onFailedTransactionsSuccessFetch("No transaction data available.")
                    }
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    transactionsListener.onFailedTransactionsSuccessFetch(
                        response.message ?: "An error occurred while fetching transactions."
                    )
                }

                override fun onNetworkFailure(t: Throwable) {
                    transactionsListener.onFailedTransactionsSuccessFetch(
                        t.message ?: "A network error occurred."
                    )
                }
            }
        )
    }
}