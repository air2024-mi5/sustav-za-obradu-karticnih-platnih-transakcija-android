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
        "MAESTRO" -> R.drawable.maestro
        "VISA" -> R.drawable.visa
        "MASTERCARD" -> R.drawable.mastercard
        "DINERS" -> R.drawable.diners
        "DISCOVER" -> R.drawable.discover
        "AMEX" -> R.drawable.amex
        else -> R.drawable.logo
    }

    fun getBorderColor(status: String): Color = when (status) {
        "00" -> Color.Green
        "11" -> Color.Green
        else -> Color.Red
    }
}