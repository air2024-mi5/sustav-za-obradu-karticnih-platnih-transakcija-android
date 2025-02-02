package hr.foi.air.szokpt.core.network.models

class ErrorResponseBody(
    success: Boolean,
    message: String,
    val error_message: String
) : ResponseBody(success, message)