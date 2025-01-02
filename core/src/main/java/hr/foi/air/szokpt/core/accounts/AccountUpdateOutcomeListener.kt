package hr.foi.air.szokpt.core.accounts

interface AccountUpdateOutcomeListener {
    fun onSuccessfulAccountUpdate(successMessage: String)
    fun onFailedAccountUpdate(failureMessage: String)
}