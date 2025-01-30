package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName

data class ProcessingPageResponse(
    @SerializedName("transactions") val processingList: List<ProcessingResponse>,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("totalPages") val totalPages: Int
)