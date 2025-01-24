package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.ProcessingResponse
import retrofit2.Call

class RevertLastProcessingRequestHandler(private val jwtToken: String) :
    TemplateRequestHandler<ProcessingResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<ProcessingResponse>> {
        val service = NetworkService.processingService
        return service.revertLastProcessing("Bearer $jwtToken")
    }
}