package foi.air.szokpt.utils

import hr.foi.air.szokpt.ws.models.responses.User

class UserDataValidator : Validator<User> {
    override fun validate(data: User): ValidationResult {
        return when {
            !validateField(data.username) -> ValidationResult(
                false,
                "Username must be entered!"
            )

            !validateField(data.firstName) -> ValidationResult(
                false,
                "First name must be entered!"
            )

            !validateField(data.lastName) -> ValidationResult(
                false,
                "Last name must be entered!"
            )

            !validateField(data.email) -> ValidationResult(
                false,
                "Email must be entered!"
            )

            !validatePasswordLength(data.password) -> ValidationResult(
                false,
                "Password must contain at least 3 characters."
            )

            !validateEmailFormat(data.email) -> ValidationResult(
                false,
                "The email must be in the correct format."
            )

            else -> ValidationResult(true)
        }
    }

    private fun validateField(field: String): Boolean {
        return field.isNotBlank()
    }

    private fun validatePasswordLength(password: String?): Boolean {
        if (!password.isNullOrBlank()) {
            return password.length >= 3
        }
        return true
    }

    private fun validateEmailFormat(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
