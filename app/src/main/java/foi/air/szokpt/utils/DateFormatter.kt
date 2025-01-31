package foi.air.szokpt.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    private val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    private val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm'h'")

    fun format(dateTime: String): String {
        return try {
            val parsedDate = LocalDateTime.parse(dateTime, inputFormatter)
            parsedDate.format(outputFormatter)
        } catch (e: Exception) {
            "Invalid date format"
        }
    }

    fun format(localDateTime: LocalDateTime): String {
        return localDateTime.format(outputFormatter)
    }

    fun parse(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime, inputFormatter)
    }
}