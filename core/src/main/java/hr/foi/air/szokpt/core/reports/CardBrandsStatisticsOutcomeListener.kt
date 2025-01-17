package hr.foi.air.szokpt.core.reports

interface CardBrandsStatisticsOutcomeListener {
    fun onSuccessfulCardBrandsStatisticsFetch(
        totalTransactions: Int,
        visaCount: Int,
        dinersCount: Int,
        discoverCount: Int,
        maestroCount: Int,
        amexCount: Int,
        mastercardCount: Int,
    )

    fun onFailedCardBrandsStatisticsFetch(
        failureMessage: String
    )
}