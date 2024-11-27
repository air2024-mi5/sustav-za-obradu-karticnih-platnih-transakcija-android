package foi.air.szokpt.helpers

import android.util.Base64
import com.google.gson.Gson
import foi.air.szokpt.network.models.JwtData

object JwtUtils {
    fun decodeJwtPayload(token: String?): JwtData {
        val parts = token?.split(".")
        if (parts == null || parts.size != 3) {
            throw IllegalArgumentException("Invalid JWT token")
        }

        val payloadEncoded = parts[1]
        val payloadDecodedBytes = Base64.decode(payloadEncoded, Base64.URL_SAFE or Base64.NO_WRAP)
        val payloadString = String(payloadDecodedBytes, Charsets.UTF_8)
        return Gson().fromJson(payloadString, JwtData::class.java)
    }
}