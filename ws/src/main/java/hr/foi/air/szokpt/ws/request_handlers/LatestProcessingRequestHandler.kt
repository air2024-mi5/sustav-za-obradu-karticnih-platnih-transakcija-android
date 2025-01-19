package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.LatestProcessingResponse
import retrofit2.Call

class LatestProcessingRequestHandler(private val jwtToken: String) :
    TemplateRequestHandler<LatestProcessingResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<LatestProcessingResponse>> {
        val service = NetworkService.processingService
        return service.getLatestProcessingResult("Bearer $jwtToken")
    }
}