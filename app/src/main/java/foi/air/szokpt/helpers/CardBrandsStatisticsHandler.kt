package foi.air.szokpt.helpers

import android.util.Log
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.reports.CardBrandsStatisticsOutcomeListener
import hr.foi.air.szokpt.ws.models.CardBrandsStatisticsResponse
import hr.foi.air.szokpt.ws.request_handlers.CardBrandsStatisticsRequestHandler

class CardBrandsStatisticsHandler() {
    fun getCardBrandsStatistics(
        jwtToken: String,
        cardBrandsStatisticsOutcomeListener: CardBrandsStatisticsOutcomeListener
    ) {
        val cardBrandsStatisticsRequestHandler = CardBrandsStatisticsRequestHandler(jwtToken)

        cardBrandsStatisticsRequestHandler.sendRequest(
            object : ResponseListener<CardBrandsStatisticsResponse> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<CardBrandsStatisticsResponse>) {
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    cardBrandsStatisticsOutcomeListener.onFailedCardBrandsStatisticsFetch(
                        "An error occurred while fetching card brands statistics: ${response.message}"
                            ?: "An error occurred while fetching card brands statistics."
                    )
                    Log.d("CARDBRANDS", "An error occurred while fetching card brands statistics.")
                }

                override fun onNetworkFailure(t: Throwable) {
                    cardBrandsStatisticsOutcomeListener.onFailedCardBrandsStatisticsFetch(
                        "Network error: $t.message" ?: "A network error occurred."
                    )
                    Log.d(
                        "CARDBRANDS",
                        "A NETWORK error occurred while fetching card brands statistics."
                    )
                }
            }
        )
    }
}