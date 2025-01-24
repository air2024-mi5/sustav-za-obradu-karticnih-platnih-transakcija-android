package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import retrofit2.Call

class RevertLastProcessingRequestHandler(private val jwtToken: String) :
    TemplateRequestHandler<Unit>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Unit>> {
        val service = NetworkService.processingService
        return service.revertLastProcessing("Bearer $jwtToken")
    }
}