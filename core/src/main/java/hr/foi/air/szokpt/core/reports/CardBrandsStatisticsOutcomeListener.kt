package hr.foi.air.szokpt.core.reports

interface CardBrandsStatisticsOutcomeListener {
    fun onSuccessfulCardBrandsStatisticsFetch(
        cardBrandsStatisticsData: CardBrandsStatisticsData
    )

    fun onFailedCardBrandsStatisticsFetch(
        failureMessage: String
    )
}