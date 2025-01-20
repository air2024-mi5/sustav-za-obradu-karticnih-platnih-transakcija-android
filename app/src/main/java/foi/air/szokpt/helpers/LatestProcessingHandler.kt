package foi.air.szokpt.helpers

import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.processing.LatestProcessingOutcomeListener
import hr.foi.air.szokpt.ws.models.LatestProcessingResponse
import hr.foi.air.szokpt.ws.models.responses.toCoreBatchRecord
import hr.foi.air.szokpt.ws.request_handlers.LatestProcessingRequestHandler

class LatestProcessingHandler {
    fun getLatestProcessing(
        jwtToken: String,
        latestProcessingOutcomeListener: LatestProcessingOutcomeListener
    ) {
        val latestProcessingRequestHandler = LatestProcessingRequestHandler(jwtToken)

        latestProcessingRequestHandler.sendRequest(object :
            ResponseListener<LatestProcessingResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<LatestProcessingResponse>) {
                val latestProcessing = response.data!![0]
                latestProcessingOutcomeListener.onSuccessfulLatestProcessingFetch(
                    status = latestProcessing.status,
                    scheduledAt = latestProcessing.scheduledAt,
                    processedAt = latestProcessing.processedAt,
                    batchRecords = latestProcessing.batchRecords.map { it.toCoreBatchRecord() },
                    processedTransactionsCount = latestProcessing.processedTransactionsCount
                )
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                latestProcessingOutcomeListener.onFailedLatestProcessingFetch(
                    "An error occurred while fetching latest processing: ${response.message}"
                        ?: "An error occurred while fetching latest processing."
                )
            }

            override fun onNetworkFailure(t: Throwable) {
                latestProcessingOutcomeListener.onFailedLatestProcessingFetch(
                    "Network error: $t.message" ?: "A network error occurred."
                )
            }
        })
    }
}