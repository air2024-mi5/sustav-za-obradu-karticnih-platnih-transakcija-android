import com.google.gson.GsonBuilder
import hr.foi.air.szokpt.ws.network.AuthenticationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val instance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val authenticationService: AuthenticationService = instance.create(AuthenticationService::class.java)

}