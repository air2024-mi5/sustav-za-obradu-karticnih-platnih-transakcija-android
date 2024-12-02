package hr.foi.air.core.network.models

class ErrorResponseBody(
    success: Boolean,
    message: String,
    val error_code: Int,
    val error_message: String
) : ResponseBody(success, message)