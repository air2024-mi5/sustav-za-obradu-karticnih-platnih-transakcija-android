import hr.foi.air.szokpt.ws.network.AuthenticationService
import hr.foi.air.szokpt.ws.network.NetworkConfig
import hr.foi.air.szokpt.ws.network.ProcessingService
import hr.foi.air.szokpt.ws.network.ReportsService
import hr.foi.air.szokpt.ws.network.TransactionsService
import hr.foi.air.szokpt.ws.network.UsersService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private val BASE_URL_ACCOUNT_MNG = NetworkConfig.BASE_URL + NetworkConfig.ACCOUNT_MNG_PORT
    private val BASE_URL_TRANSACTIONS_MNG =
        NetworkConfig.BASE_URL + NetworkConfig.TRANSACTIONS_MNG_PORT
    private val BASE_URL_REPORTS = NetworkConfig.BASE_URL + NetworkConfig.REPORTS_PORT
    private val BASE_URL_PROCESSING = NetworkConfig.BASE_URL + NetworkConfig.PROCESSING_PORT

    private val accountsInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_ACCOUNT_MNG)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val transmngInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_TRANSACTIONS_MNG)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val reportsInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_REPORTS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val processingInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_PROCESSING)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authenticationService: AuthenticationService =
        accountsInstance.create(AuthenticationService::class.java)

    val usersService: UsersService =
        accountsInstance.create(UsersService::class.java)

    val transactionsService: TransactionsService =
        transmngInstance.create(TransactionsService::class.java)

    val reportsService: ReportsService = reportsInstance.create(ReportsService::class.java)

    val processingService: ProcessingService =
        processingInstance.create(ProcessingService::class.java)
}