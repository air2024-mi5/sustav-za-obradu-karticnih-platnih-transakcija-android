package foi.air.szokpt.helpers

import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.reports.TransactionsPerDayOutcomeListener
import hr.foi.air.szokpt.ws.models.TransactionsPerDayResponse
import hr.foi.air.szokpt.ws.request_handlers.TransactionsPerDayRequestHandler
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionsPerDayHandler {
    fun getTransactionsPerDay(
        jwtToken: String,
        transactionsPerDayOutcomeListener: TransactionsPerDayOutcomeListener
    ) {
        val transactionsPerDayRequestHandler = TransactionsPerDayRequestHandler(jwtToken)

        transactionsPerDayRequestHandler.sendRequest(
            object : ResponseListener<TransactionsPerDayResponse> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionsPerDayResponse>) {
                    if (response.data != null && response.data!!.isNotEmpty()) {
                        val transactionData = response.data!![0]
                        transactionsPerDayOutcomeListener.onSuccessfulTransactionsPerDayFetch(
                            totalTransactions = transactionData.totalTransactions,
                            transactionsPerDay = transactionData.transactionsPerDay.mapKeys { (dateString, _) ->
                                Timestamp(
                                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
                                        .parse(dateString.toString())?.time ?: 0
                                )
                            }
                        )
                    } else {
                        transactionsPerDayOutcomeListener.onFailedTransactionsPerDayFetch(
                            "No transactions per day statistics data available."
                        )
                    }
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    transactionsPerDayOutcomeListener.onFailedTransactionsPerDayFetch(
                        "An error occurred while fetching transactions: ${response.message}"
                            ?: "An error occurred while fetching transactions."
                    )
                }

                override fun onNetworkFailure(t: Throwable) {
                    transactionsPerDayOutcomeListener.onFailedTransactionsPerDayFetch(
                        "Network error: $t.message" ?: "A network error occurred."
                    )
                }
            }
        )
    }
}