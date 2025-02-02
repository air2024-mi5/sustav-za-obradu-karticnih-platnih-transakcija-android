package hr.foi.air.szokpt.core.reports

data class CardBrandsStatisticsData(
    val totalTransactions: Int,
    val visaCount: Int,
    val dinersCount: Int,
    val discoverCount: Int,
    val maestroCount: Int,
    val amexCount: Int,
    val mastercardCount: Int,
)