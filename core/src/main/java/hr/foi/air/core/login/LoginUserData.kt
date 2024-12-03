package hr.foi.air.core.login

data class LoginUserData(
    val username: String,
    val role: String,
    val token: String
    )
