package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.core.network.ResponseListener
import hr.foi.air.core.network.models.ErrorResponseBody
import hr.foi.air.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import hr.foi.air.szokpt.ws.request_handlers.TransactionsRequestHandler

class TransactionViewModel() : ViewModel() {
    private val _transactionPage: MutableLiveData<TransactionPageResponse?> = MutableLiveData(null)
    private val _currentPage: MutableLiveData<Int?> = MutableLiveData(1)
    private val _totalPages: MutableLiveData<Int?> = MutableLiveData(1)

    val transactionPage: LiveData<TransactionPageResponse?> = _transactionPage
    val currentPage: LiveData<Int?> = _currentPage
    val totalPages: LiveData<Int?> = _totalPages

    fun fetchTransactions(page: Int) {
        val transactionsRequestHandler = TransactionsRequestHandler(page)

        transactionsRequestHandler.sendRequest(object : ResponseListener<TransactionPageResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionPageResponse>) {
                val transactionPage = response.data
                _transactionPage.value = transactionPage?.first()
                _currentPage.value = _transactionPage.value?.currentPage
                _totalPages.value = _transactionPage.value?.totalPages
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