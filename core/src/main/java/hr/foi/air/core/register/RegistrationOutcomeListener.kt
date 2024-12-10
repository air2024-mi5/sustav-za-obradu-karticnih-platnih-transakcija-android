package hr.foi.air.core.register

interface RegistrationOutcomeListener {
    fun onSuccessfulRegistration(successMessage: String)
    fun onFailedRegistration(failureMessage: String)
}