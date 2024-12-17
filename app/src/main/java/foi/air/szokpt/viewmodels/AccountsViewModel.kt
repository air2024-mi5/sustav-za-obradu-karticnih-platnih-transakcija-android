import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import hr.foi.air.core.network.ResponseListener
import hr.foi.air.core.network.models.ErrorResponseBody
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.User
import hr.foi.air.szokpt.ws.request_handlers.GetUsersRequestHandler

class UsersViewModel : ViewModel() {
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _accounts: MutableLiveData<List<User>> = MutableLiveData(mutableListOf())
    val accounts: LiveData<List<User>> = _accounts

    fun fetchUsers() {
        val token = Auth.logedInUserData?.token
        if (token == null) return //_MESSAGE
        val usersRequestHandler = GetUsersRequestHandler(token)
        usersRequestHandler.sendRequest(object : ResponseListener<User> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<User>) {
                _loading.value = true
                _accounts.value = response.data!!.toMutableList()
                _loading.value = false
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                println("Error receiving response: ${response.error_message}")
            }

            override fun onNetworkFailure(t: Throwable) {
                println("Error contacting network...")
            }
        })
    }
}