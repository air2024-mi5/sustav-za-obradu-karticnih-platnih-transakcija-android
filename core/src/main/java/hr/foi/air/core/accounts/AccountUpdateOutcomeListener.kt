package hr.foi.air.core.accounts

interface AccountUpdateOutcomeListener {
    fun onSuccessfulAccountUpdate(successMessage: String)
    fun onFailedAccountUpdate(failureMessage: String)
}