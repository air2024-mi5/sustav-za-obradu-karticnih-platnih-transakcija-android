package foi.air.szokpt.helpers

import foi.air.szokpt.models.Transaction
import kotlin.random.Random

class MockDataLoader {

    fun loadTransactions(): List<Transaction> {
        return List(44) { index ->
            val randomValue = Random.nextInt(0, 6)

            val type = when (randomValue % 3) {
                0 -> "MasterCard"
                1 -> "Visa"
                else -> "American Express"
            }

            val description = when (randomValue % 5) {
                0 -> "FOI"
                1 -> "Sjever"
                2 -> "FER"
                3 -> "TVZ"
                else -> "FERIT"
            }

            Transaction(
                type = type,
                description = description
            )
        }
    }
}
