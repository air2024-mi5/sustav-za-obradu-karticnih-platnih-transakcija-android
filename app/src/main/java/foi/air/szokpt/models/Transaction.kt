package foi.air.szokpt.models

enum class TransactionType {
    Visa,
    MasterCard,
    AmericanExpress,
}

data class Transaction(
    val type: TransactionType,
    val description: String
)

