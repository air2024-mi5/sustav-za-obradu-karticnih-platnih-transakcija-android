package foi.air.szokpt.utils

import hr.foi.air.szokpt.ws.models.responses.Transaction
import hr.foi.air.szokpt.ws.models.responses.User
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TransactionDataValidator : Validator<Transaction> {
    override fun validate(data: Transaction): ValidationResult {
        return when {
            !validateField(data.amount) -> ValidationResult(
                false,
                "Amount must be positive!"
            )

            !validateField(data.transactionTimestamp) -> ValidationResult(
                false,
                "Timestamp must be entered!"
            )

            !validateTimestampFormat(data.transactionTimestamp) -> ValidationResult(
                false,
                "Timestamp must be in the format yyyy-MM-dd HH:mm:ss!"
            )

            !isTimestampNotInFuture(data.transactionTimestamp) -> ValidationResult(
                false,
                "Timestamp must not be in the future!"
            )

            else -> ValidationResult(true)
        }
    }

    private fun validateField(field: String): Boolean {
        return field.isNotBlank()
    }

    private fun validateField(field: Double): Boolean {
        return field > 0
    }

    private fun validateTimestampFormat(timestamp: String): Boolean {
        val regex = Regex("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
        return regex.matches(timestamp)
    }

    private fun isTimestampNotInFuture(timestamp: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return try {
            val inputTime = LocalDateTime.parse(timestamp, formatter)

            val currentTime = LocalDateTime.now(ZoneId.systemDefault())

            !inputTime.isAfter(currentTime)
        } catch (e: Exception) {
            false
        }
    }
}