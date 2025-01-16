package foi.air.szokpt.utils

import hr.foi.air.szokpt.ws.models.responses.Transaction
import java.util.UUID

class MockTransactionsLoader {
    fun getMockTransactions(): List<Transaction> {
        return listOf(
            Transaction(
                guid = UUID.randomUUID(),
                maskedPan = "165650****0054",
                responseCode = "00",
                cardBrand = "Visa",
                currency = "840",
                amount = 50.0,
                transactionTimestamp = "12/01/2025 10:03:17",
                trxType = "Purchase",
                installmentsNumber = 1,
                installmentsCreditor = "",
                pinUsed = false,
                approvalCode = "00",
                processed = false
            ),
            Transaction(
                guid = UUID.randomUUID(),
                maskedPan = "165650****0054",
                responseCode = "00",
                cardBrand = "Visa",
                currency = "840",
                amount = 50.0,
                transactionTimestamp = "12/01/2025 10:03:17",
                trxType = "Purchase",
                installmentsNumber = 1,
                installmentsCreditor = "",
                pinUsed = false,
                approvalCode = "00",
                processed = false
            ),
            Transaction(
                guid = UUID.randomUUID(),
                maskedPan = "165650****0054",
                responseCode = "00",
                cardBrand = "Visa",
                currency = "840",
                amount = 50.0,
                transactionTimestamp = "12/01/2025 10:03:17",
                trxType = "Purchase",
                installmentsNumber = 1,
                installmentsCreditor = "",
                pinUsed = false,
                approvalCode = "00",
                processed = false
            ),
            Transaction(
                guid = UUID.randomUUID(),
                maskedPan = "765230****0054",
                responseCode = "00",
                cardBrand = "Visa",
                currency = "978",
                amount = 100.0,
                transactionTimestamp = "12/01/2025 09:03:17",
                trxType = "Purchase",
                installmentsNumber = 1,
                installmentsCreditor = "",
                pinUsed = false,
                approvalCode = "00",
                processed = false
            )
        )
    }
}