import hr.foi.air.szokpt.ws.network.AuthenticationService
import hr.foi.air.szokpt.ws.network.TransactionsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL_ACCOUNTS = "http://10.0.2.2:8080/"
    private const val BASE_URL_TRANSMNG = "http://10.0.2.2:8081/"

    private val accountsInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_ACCOUNTS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val transmngInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_TRANSMNG)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authenticationService: AuthenticationService =
        accountsInstance.create(AuthenticationService::class.java)

    val transactionsService: TransactionsService =
        transmngInstance.create(TransactionsService::class.java)
}