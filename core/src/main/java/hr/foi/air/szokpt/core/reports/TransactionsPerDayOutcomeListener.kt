package hr.foi.air.szokpt.core.reports

import java.sql.Timestamp

interface TransactionsPerDayOutcomeListener {
    fun onSuccessfulTransactionsPerDayFetch(
        totalTransactions: Int,
        transactionsPerDay: Map<Timestamp, Int>
    )

    fun onFailedTransactionsPerDayFetch(
        failureMessage: String
    )
}