package foi.air.szokpt.utils

import androidx.compose.ui.graphics.Color
import foi.air.szokpt.models.CardBrandInformation
import hr.foi.air.szokpt.core.reports.CardBrandsStatisticsData

class CardBrandInformationList() {
    fun getCardBrandInformationList(cardBrandsStats: CardBrandsStatisticsData): List<CardBrandInformation> {
        return listOf(
            CardBrandInformation(
                "VISA",
                cardBrandsStats.visaCount ?: 0,
                Color(0xFF1634CC)
            ),
            CardBrandInformation(
                "DINERS",
                cardBrandsStats.dinersCount ?: 0,
                Color(0xFFc5c5c7)
            ),
            CardBrandInformation(
                "DISCOVER",
                cardBrandsStats.discoverCount ?: 0, Color(0xFFff7001)
            ),
            CardBrandInformation(
                "MAESTRO",
                cardBrandsStats.maestroCount ?: 0,
                Color(0xFF00a2e5)
            ),
            CardBrandInformation(
                "AMEX",
                cardBrandsStats.amexCount ?: 0,
                Color(0xFF006cb7)
            ),
            CardBrandInformation(
                "MASTERCARD",
                cardBrandsStats.mastercardCount ?: 0,
                Color(0xFFf79e1b)
            )
        )
    }
}