package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("message") val message: String?,
)
