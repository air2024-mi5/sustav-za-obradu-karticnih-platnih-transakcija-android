package hr.foi.air.szokpt.ws.models.responses

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("role") val role: UserRole,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("blocked") val blocked: Boolean,
    @SerializedName("deactivated") val deactivated: Boolean,
)