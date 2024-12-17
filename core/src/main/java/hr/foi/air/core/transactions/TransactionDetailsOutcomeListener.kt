package hr.foi.air.core.transactions

interface TransactionDetailsOutcomeListener {

    fun onSuccessfulTransactionDetailsFetch(
        transactionData: TransactionData
    )

    fun onFailedTransactionDetailsFetch(
        failureMessage: String
    )
}