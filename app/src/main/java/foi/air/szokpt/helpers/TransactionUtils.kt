package foi.air.szokpt.helpers

import androidx.compose.ui.graphics.Color
import foi.air.szokpt.R

object TransactionUtils {
    fun getCurrencySymbol(currencyCode: String): String = when (currencyCode) {
        "840" -> "$"
        "978" -> "€"
        else -> ""
    }

    fun getCurrencyColor(currencyCode: String): Color = when (currencyCode) {
        "840" -> Color.Green
        "978" -> Color.Yellow
        else -> Color.White
    }

    fun getTransactionTypeDisplay(type: String): String? = transactionTypeMap[type]

    private val transactionTypeMap = mapOf(
        "sale" to "Sale",
        "refund" to "Refund",
        "void_sale" to "Void sale",
        "void_refund" to "Void refund",
        "reversal_sale" to "Reversal sale",
        "reversal_refund" to "Reversal refund"
    )

    fun getCardBrandDrawable(cardBrand: String): Int = when (cardBrand) {
        "Maestro" -> R.drawable.maestro
        "Visa" -> R.drawable.visa
        "MasterCard" -> R.drawable.mastercard
        "Diners" -> R.drawable.diners
        "Discover" -> R.drawable.discover
        else -> R.drawable.logo
    }
}