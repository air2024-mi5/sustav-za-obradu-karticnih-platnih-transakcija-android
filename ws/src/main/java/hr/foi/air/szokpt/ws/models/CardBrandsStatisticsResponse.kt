package hr.foi.air.szokpt.ws.models

import com.google.gson.annotations.SerializedName

data class CardBrandsStatisticsResponse(
    @SerializedName("totalTransactions") val totalTransactions: Int,
    @SerializedName("visaCount") val visaCount: Int,
    @SerializedName("dinersCount") val dinersCount: Int,
    @SerializedName("discoverCount") val discoverCount: Int,
    @SerializedName("maestroCount") val maestroCount: Int,
    @SerializedName("amexCount") val amexCount: Int,
    @SerializedName("mastercardCount") val mastercardCount: Int,
)