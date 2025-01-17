package foi.air.szokpt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.helpers.CardBrandsStatisticsHandler
import hr.foi.air.szokpt.core.reports.CardBrandsStatisticsData
import hr.foi.air.szokpt.core.reports.CardBrandsStatisticsOutcomeListener

class CardBrandsStatisticsViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: MutableLiveData<String?> get() = _errorMessage

    private val _cardBrandsStatisticsData = MutableLiveData<CardBrandsStatisticsData?>(null)
    val cardBrandsStatisticsData: MutableLiveData<CardBrandsStatisticsData?> get() = _cardBrandsStatisticsData

    private val cardBrandsStatisticsHandler = CardBrandsStatisticsHandler()

    fun fetchCardBrandsStatistics() {
        cardBrandsStatisticsHandler.getCardBrandsStatistics(object :
            CardBrandsStatisticsOutcomeListener {
            override fun onSuccessfulCardBrandsStatisticsFetch(cardBrandsStatisticsData: CardBrandsStatisticsData) {
                _cardBrandsStatisticsData.value = cardBrandsStatisticsData
                _errorMessage.value = null
            }

            override fun onFailedCardBrandsStatisticsFetch(failureMessage: String) {
                _errorMessage.value = failureMessage
                _cardBrandsStatisticsData.value = null
            }
        })
    }
}