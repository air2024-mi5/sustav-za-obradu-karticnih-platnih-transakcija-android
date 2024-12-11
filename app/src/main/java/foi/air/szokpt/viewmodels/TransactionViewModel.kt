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
    val transactionPage: LiveData<TransactionPageResponse?> = _transactionPage

    fun fetchTransactions(page: Int) {
        val transactionsRequestHandler = TransactionsRequestHandler(page)

        transactionsRequestHandler.sendRequest(object : ResponseListener<TransactionPageResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionPageResponse>) {
                val transactionPage = response.data
                _transactionPage.value = transactionPage?.first()
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