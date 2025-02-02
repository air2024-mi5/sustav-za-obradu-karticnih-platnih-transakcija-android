package hr.foi.air.szokpt.ws.models.responses

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class SelectedTransaction(
    @SerializedName("guid") val guid: UUID,
    @SerializedName("selected_by") val selectedBy: String
)