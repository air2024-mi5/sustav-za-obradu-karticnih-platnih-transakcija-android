package hr.foi.air.szokpt.core.register

interface RegistrationOutcomeListener {
    fun onSuccessfulRegistration(successMessage: String)
    fun onFailedRegistration(failureMessage: String)
}