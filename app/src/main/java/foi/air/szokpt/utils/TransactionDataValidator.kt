package foi.air.szokpt.utils

import hr.foi.air.szokpt.core.transactions.TransactionData
import hr.foi.air.szokpt.ws.models.responses.Transaction
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TransactionDataValidator : Validator<TransactionData> {
    override fun validate(data: TransactionData): ValidationResult {
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
                "Timestamp must be in the format dd/MM/yyyy HH:mm:ss!"
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
        val regex = Regex("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}\\s(0?[0-9]|1[0-9]|2[0-3]):(0?[0-9]|[1-5][0-9]):(0?[0-9]|[1-5][0-9])\$")
        val x= regex.matches(timestamp)
        return x;
    }

    private fun isTimestampNotInFuture(timestamp: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

        return try {
            val inputTime = LocalDateTime.parse(timestamp, formatter)

            val currentTime = LocalDateTime.now(ZoneId.systemDefault())

            !inputTime.isAfter(currentTime)
        } catch (e: Exception) {
            false
        }
    }
}