package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import hr.foi.air.core.network.ResponseListener
import hr.foi.air.core.network.models.ErrorResponseBody
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.User
import hr.foi.air.szokpt.ws.request_handlers.GetAccountsRequestHandler

class AccountsViewModel : ViewModel() {
    private val _accounts: MutableLiveData<List<User>> = MutableLiveData(mutableListOf())
    private val _loading = MutableLiveData(true)
    private val _message: MutableLiveData<String> = MutableLiveData("")

    val accounts: LiveData<List<User>> = _accounts
    val loading: LiveData<Boolean> = _loading
    val message: MutableLiveData<String> = _message

    fun fetchUsers() {
        val token = Auth.logedInUserData?.token
        if (token == null) showErrorMessage("Something went wrong! Please try logging in again!")
        val usersRequestHandler = GetAccountsRequestHandler(token!!)
        usersRequestHandler.sendRequest(object : ResponseListener<User> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<User>) {
                _loading.value = true
                _accounts.value = response.data!!.toMutableList()
                _loading.value = false
                _message.value = ""
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                showErrorMessage("Something went wrong!")
            }

            override fun onNetworkFailure(t: Throwable) {
                showErrorMessage("Please check your internet connection!")
            }
        })
    }

    private fun showErrorMessage(message: String) {
        _message.value = message
        _loading.value = false
    }
}