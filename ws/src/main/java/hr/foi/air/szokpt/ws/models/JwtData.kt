package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName

data class JwtData(
    @SerializedName("sub") var username: String,
    @SerializedName("role") var role: String,
    @SerializedName("iat") var iat: Long,
    @SerializedName("exp") var exp: Long
)
