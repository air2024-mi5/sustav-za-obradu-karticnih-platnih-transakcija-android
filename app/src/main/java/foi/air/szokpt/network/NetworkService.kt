import com.google.gson.GsonBuilder
import foi.air.szokpt.network.AuthenticationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val gson = GsonBuilder().setLenient().create()

    private val instance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    val authenticationService: AuthenticationService = instance.create(AuthenticationService::class.java)

}