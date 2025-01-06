package foi.air.szokpt.helpers

import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.TransactionData
import hr.foi.air.szokpt.core.transactions.TransactionDetailsOutcomeListener
import hr.foi.air.szokpt.ws.models.responses.Transaction
import hr.foi.air.szokpt.ws.request_handlers.TransactionDetailsRequestHandler

class TransactionDetailsHandler() {
    fun getTransactionDetails(
        transactionId: Int,
        transactionDetailsListener: TransactionDetailsOutcomeListener
    ) {
        val transactionDetailsRequestHandler = TransactionDetailsRequestHandler(transactionId)

        transactionDetailsRequestHandler.sendRequest(
            object : ResponseListener<Transaction> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<Transaction>) {
                    val transaction = response.data?.get(0)
                    if (transaction != null) {
                        transactionDetailsListener.onSuccessfulTransactionDetailsFetch(
                            transactionData = TransactionData(
                                id = transaction.id,
                                amount = transaction.amount,
                                currency = transaction.currency,
                                trxType = transaction.trxType,
                                installmentsNumber = transaction.installmentsNumber,
                                installmentsCreditor = transaction.installmentsCreditor,
                                cardBrand = transaction.cardBrand,
                                transactionTimestamp = transaction.transactionTimestamp,
                                maskedPan = transaction.maskedPan,
                                pinUsed = transaction.pinUsed,
                                responseCode = transaction.responseCode,
                                processed = transaction.processed
                            )
                        )
                    } else {
                        transactionDetailsListener.onFailedTransactionDetailsFetch("Transaction data is null or empty")
                    }
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    transactionDetailsListener.onFailedTransactionDetailsFetch(
                        "An error occurred while fetching transaction details: $response.message"
                            ?: "An error occurred while fetching transaction details."
                    )
                }

                override fun onNetworkFailure(t: Throwable) {
                    transactionDetailsListener.onFailedTransactionDetailsFetch(
                        "Network error: $t.message" ?: "A network error occurred."
                    )
                }
            }
        )
    }
}