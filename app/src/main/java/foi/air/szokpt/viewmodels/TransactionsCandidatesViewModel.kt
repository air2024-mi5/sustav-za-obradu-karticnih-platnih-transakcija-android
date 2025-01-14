package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.TransactionFilter
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import hr.foi.air.szokpt.ws.models.responses.SelectedTransaction
import hr.foi.air.szokpt.ws.models.responses.Transaction
import hr.foi.air.szokpt.ws.request_handlers.AddSelectedTransactions
import hr.foi.air.szokpt.ws.request_handlers.GetSelectedTransactionsRequestHandler
import hr.foi.air.szokpt.ws.request_handlers.GetTransactionsPageRequestHandler
import java.util.UUID

class TransactionsCandidatesViewModel : ViewModel() {
    private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData(emptyList())

    private val _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    private val _totalPages: MutableLiveData<Int> = MutableLiveData(1)
    private val _selectedGuids: MutableLiveData<List<UUID>> = MutableLiveData(mutableListOf())

    private val _selectedTransactions: MutableLiveData<List<SelectedTransaction>> =
        MutableLiveData(mutableListOf())

    val transactions: LiveData<List<Transaction>> = _transactions
    val currentPage: LiveData<Int> = _currentPage
    val totalPages: LiveData<Int> = _totalPages
    val selectedGuids: LiveData<List<UUID>> = _selectedGuids

    fun fetchTransactionPage(page: Int) {
        val jwtToken = Auth.logedInUserData?.token ?: return

        val transactionsRequestHandler =
            GetTransactionsPageRequestHandler(jwtToken, page, filter = setFilter())
        transactionsRequestHandler.sendRequest(object : ResponseListener<TransactionPageResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionPageResponse>) {
                val transactionPage = response.data?.firstOrNull()

                val selectedGuids = _selectedTransactions.value?.map { it.guid }?.toSet().orEmpty()

                val filteredTransactions = transactionPage?.transactions?.filter { transaction ->
                    transaction.guid !in selectedGuids
                } ?: emptyList()

                _transactions.value = filteredTransactions
                _currentPage.value = transactionPage?.currentPage
                _totalPages.value = transactionPage?.totalPages
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                println("Error receiving response: ${response.error_message}")
            }

            override fun onNetworkFailure(t: Throwable) {
                println("Error contacting network...")
            }
        })
    }

    fun fetchSelectedTransactions() {
        val jwtToken = Auth.logedInUserData?.token ?: return

        val selectedTransactionsRequestHandler = GetSelectedTransactionsRequestHandler(jwtToken)
        selectedTransactionsRequestHandler.sendRequest(object :
            ResponseListener<SelectedTransaction> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<SelectedTransaction>) {
                _selectedTransactions.value = response.data!!.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                println("Error receiving response: ${response.error_message}")
            }

            override fun onNetworkFailure(t: Throwable) {
                println("Error contacting network...")
            }
        })
    }

    fun addSelectedTransactions() {
        val jwtToken = Auth.logedInUserData?.token ?: return

        val addSelectedTransactionsRequestHandler =
            AddSelectedTransactions(jwtToken, _selectedGuids.value!!)
        addSelectedTransactionsRequestHandler.sendRequest(object :
            ResponseListener<Unit> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Unit>) {
                println("Success")
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                println("Error receiving response: ${response.error_message}")
            }

            override fun onNetworkFailure(t: Throwable) {
                println("Error contacting network...")
            }
        })
    }

    fun toggleSelectAllTransactions(pageGuids: Set<UUID>) {
        _selectedGuids.value = _selectedGuids.value.orEmpty().let { currentSelectedGuids ->
            if (pageGuids.all { it in currentSelectedGuids }) {
                currentSelectedGuids - pageGuids
            } else {
                currentSelectedGuids + pageGuids
            }
        }
    }

    fun updateSelectionStatus(transactionId: UUID, isSelected: Boolean) {
        _selectedGuids.value = _selectedGuids.value.orEmpty().let { currentSelectedGuids ->
            if (isSelected) {
                currentSelectedGuids + transactionId
            } else {
                currentSelectedGuids - transactionId
            }
        }
    }

    fun setFilter(): TransactionFilter {
        return TransactionFilter(
            cardBrands = emptyList(),
            trxTypes = emptyList(),
            minAmount = null,
            maxAmount = null,
            afterDate = null,
            beforeDate = null,
            processed = false
        )
    }
}