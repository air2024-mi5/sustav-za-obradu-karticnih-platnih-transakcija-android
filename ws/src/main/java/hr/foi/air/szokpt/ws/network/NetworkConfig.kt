package hr.foi.air.szokpt.ws.network

object NetworkConfig {
    private const val USE_LOCAL = false;

    val BASE_URL: String
        get() = if (USE_LOCAL) "http://10.0.2.2" else "http://46.202.155.53"

    val ACCOUNT_MNG_PORT: String
        get() = if (USE_LOCAL) ":8080" else ":8080"

    val TRANSACTIONS_MNG_PORT: String
        get() = if (USE_LOCAL) ":8080" else ":8081"

    val REPORTS_PORT: String
        get() = if (USE_LOCAL) ":8080" else ":8082"

    val PROCESSING_PORT: String
        get() = if (USE_LOCAL) ":8080" else ":8083"
}