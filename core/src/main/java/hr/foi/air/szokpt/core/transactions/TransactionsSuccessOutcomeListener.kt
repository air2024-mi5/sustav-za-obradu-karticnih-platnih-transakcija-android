package hr.foi.air.szokpt.core.transactions

interface TransactionsSuccessOutcomeListener {
    fun onSuccessfulTransactionsSuccessFetch(
        totalTransactions: Int,
        successfulTransactions: Int,
        rejectedTransactions: Int,
        canceledTransactions: Int
    )
    fun onFailedTransactionsSuccessFetch(failureMessage: String)
}