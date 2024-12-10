package hr.foi.air.core.register

data class Role(val name: String)

data class RegistrationBody(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role
)
