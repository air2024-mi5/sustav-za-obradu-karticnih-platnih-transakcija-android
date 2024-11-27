package foi.air.szokpt.network.models

import com.google.gson.annotations.SerializedName

data class LoginUserData(
    val username: String,
    val role: String,
    val token: String
    )
