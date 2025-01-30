package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.ProcessingPageResponse
import retrofit2.Call

class ProcessingsRequestHandler(
    private val page: Int,
    private val jwtToken: String
) : TemplateRequestHandler<ProcessingPageResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<ProcessingPageResponse>> {
        val service = NetworkService.processingService
        return service.getAllProcessings(page = page, jwtToken)
    }
}