package hr.foi.air.szokpt.ws.models.responses

import com.google.gson.annotations.SerializedName

data class UserRole(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)