package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.responses.User
import hr.foi.air.szokpt.ws.request_handlers.GetAccountsRequestHandler

class AccountsViewModel : ViewModel() {
    private val _accounts: MutableLiveData<List<User>> = MutableLiveData(mutableListOf())
    private val _loading = MutableLiveData(true)
    private val _message: MutableLiveData<String> = MutableLiveData("")
    var isPanelActive = MutableLiveData(false)
    val filteredAccounts: MutableLiveData<List<User>> = MutableLiveData(emptyList())
    val searchQuery: MutableLiveData<String> = MutableLiveData("")
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
                updateFilteredAccounts()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                showErrorMessage("Something went wrong!")
            }

            override fun onNetworkFailure(t: Throwable) {
                showErrorMessage("Please check your internet connection!")
            }
        })
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        updateFilteredAccounts()
    }

    private fun updateFilteredAccounts() {
        val query = searchQuery.value.orEmpty()
        val accounts = _accounts.value.orEmpty()
        filteredAccounts.value = if (query.isEmpty()) {
            accounts
        } else {
            accounts.filter {
                it.firstName.contains(query, ignoreCase = true) ||
                        it.lastName.contains(query, ignoreCase = true) ||
                        it.username.contains(query, ignoreCase = true)
            }
        }
    }

    fun setActiveState(isActive: Boolean) {
        isPanelActive.value = isActive
    }

    private fun showErrorMessage(message: String) {
        _message.value = message
        _loading.value = false
    }
}