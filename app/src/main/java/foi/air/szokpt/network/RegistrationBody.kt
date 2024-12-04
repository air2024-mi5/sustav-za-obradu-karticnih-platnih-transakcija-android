package foi.air.szokpt.network

data class RegistrationBody(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: String
)
