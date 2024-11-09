package foi.air.szokpt.helpers

import foi.air.szokpt.models.Transaction
import kotlin.random.Random

/**
 * The purpose of class MockDataLoader is to simulate loading data for testing purposes.
 * This class can be used to create mock data that mimics the structure and format
 * of actual data expected in the application, without requiring a connection to
 * a live database or external data source.
 */
class MockDataLoader {

/**
 * fun loadTransactions Generates a list of mock `Transaction` objects for simulation purposes.
 */
fun loadTransactions(): List<Transaction> {
        return List(45) { index ->
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
