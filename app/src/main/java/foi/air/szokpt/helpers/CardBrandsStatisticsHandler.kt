package foi.air.szokpt.helpers

import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.reports.CardBrandsStatisticsData
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
                    if (response.data != null && response.data!!.isNotEmpty()) {
                        val statistics = response.data!![0]
                        cardBrandsStatisticsOutcomeListener.onSuccessfulCardBrandsStatisticsFetch(
                            cardBrandsStatisticsData = CardBrandsStatisticsData(
                                totalTransactions = statistics.totalTransactions,
                                visaCount = statistics.visaCount,
                                dinersCount = statistics.dinersCount,
                                discoverCount = statistics.discoverCount,
                                maestroCount = statistics.maestroCount,
                                amexCount = statistics.amexCount,
                                mastercardCount = statistics.mastercardCount
                            )
                        )
                    } else {
                        cardBrandsStatisticsOutcomeListener.onFailedCardBrandsStatisticsFetch(
                            "No card brands statistics data available."
                        )
                    }
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    cardBrandsStatisticsOutcomeListener.onFailedCardBrandsStatisticsFetch(
                        "An error occurred while fetching card brands statistics: ${response.message}"
                            ?: "An error occurred while fetching card brands statistics."
                    )
                }

                override fun onNetworkFailure(t: Throwable) {
                    cardBrandsStatisticsOutcomeListener.onFailedCardBrandsStatisticsFetch(
                        "Network error: $t.message" ?: "A network error occurred."
                    )
                }
            }
        )
    }
}