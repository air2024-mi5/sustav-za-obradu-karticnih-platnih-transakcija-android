package hr.foi.air.core.login

data class LoginBody(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
