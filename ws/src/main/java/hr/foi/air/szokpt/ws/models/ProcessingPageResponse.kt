package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName

data class ProcessingPageResponse(
    @SerializedName("transactions") val transactions: List<ProcessingResponse>,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("totalPages") val totalPages: Int
)