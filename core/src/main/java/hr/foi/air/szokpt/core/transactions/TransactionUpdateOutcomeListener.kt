package hr.foi.air.szokpt.core.transactions

interface TransactionUpdateOutcomeListener {
    fun onSuccessfulTransactionUpdate(successMessage: String)
    fun onFailedTransactionUpdate(failureMessage: String)
}