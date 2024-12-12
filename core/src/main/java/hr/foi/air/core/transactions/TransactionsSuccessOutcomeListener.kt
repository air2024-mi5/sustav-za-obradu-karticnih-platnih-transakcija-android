package hr.foi.air.core.transactions

interface TransactionsSuccessOutcomeListener {
    fun onSuccessfulTransactionsFetch(
        totalTransactions: Int,
        successfulTransactions: Int,
        rejectedTransactions: Int,
        canceledTransactions: Int
    )
    fun onFailedTransactionsFetch(failureMessage: String)
}