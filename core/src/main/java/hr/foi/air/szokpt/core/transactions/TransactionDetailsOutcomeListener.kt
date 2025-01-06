package hr.foi.air.szokpt.core.transactions

interface TransactionDetailsOutcomeListener {

    fun onSuccessfulTransactionDetailsFetch(
        transactionData: TransactionData
    )

    fun onFailedTransactionDetailsFetch(
        failureMessage: String
    )
}