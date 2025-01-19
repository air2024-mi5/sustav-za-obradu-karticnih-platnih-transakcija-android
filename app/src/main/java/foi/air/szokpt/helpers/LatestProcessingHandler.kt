package foi.air.szokpt.helpers

import android.util.Log
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.processing.LatestProcessingOutcomeListener
import hr.foi.air.szokpt.ws.models.LatestProcessingResponse
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
                Log.d("LATEST_PROCESSING", "response: $latestProcessing")
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.d("LATEST_PROCESSING", "error: ${response.message}")
                latestProcessingOutcomeListener.onFailedLatestProcessingFetch(
                    "An error occurred while fetching latest processing: ${response.message}"
                        ?: "An error occurred while fetching latest processing."
                )
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.d("LATEST_PROCESSING", "network error: ${t.message}")
                latestProcessingOutcomeListener.onFailedLatestProcessingFetch(
                    "Network error: $t.message" ?: "A network error occurred."
                )
            }
        })
    }
}