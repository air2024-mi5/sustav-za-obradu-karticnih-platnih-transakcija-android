package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import hr.foi.air.szokpt.ws.request_handlers.GetTransactionsPageRequestHandler

class TransactionsViewModel() : ViewModel() {
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _transactionPage: MutableLiveData<TransactionPageResponse?> = MutableLiveData(null)
    private val _currentPage: MutableLiveData<Int?> = MutableLiveData(1)
    private val _totalPages: MutableLiveData<Int?> = MutableLiveData(1)

    val transactionPage: LiveData<TransactionPageResponse?> = _transactionPage
    val currentPage: LiveData<Int?> = _currentPage
    val totalPages: LiveData<Int?> = _totalPages

    fun fetchTransactionPage(page: Int) {
        val jwtToken = Auth.logedInUserData?.token ?: return
        val transactionsRequestHandler = GetTransactionsPageRequestHandler(jwtToken, page)
        transactionsRequestHandler.sendRequest(object : ResponseListener<TransactionPageResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionPageResponse>) {
                _loading.value = true
                val transactionPage = response.data
                _transactionPage.value = transactionPage?.first()
                _currentPage.value = _transactionPage.value?.currentPage
                _totalPages.value = _transactionPage.value?.totalPages
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