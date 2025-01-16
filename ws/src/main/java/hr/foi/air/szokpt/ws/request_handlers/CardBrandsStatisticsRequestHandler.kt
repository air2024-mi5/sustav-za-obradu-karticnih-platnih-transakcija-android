package hr.foi.air.szokpt.ws.request_handlers

import NetworkService
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.CardBrandsStatisticsResponse
import retrofit2.Call

class CardBrandsStatisticsRequestHandler() :
    TemplateRequestHandler<CardBrandsStatisticsResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<CardBrandsStatisticsResponse>> {
        val service = NetworkService.reportsService
        return service.getCardBrandsStatistics()
    }
}
