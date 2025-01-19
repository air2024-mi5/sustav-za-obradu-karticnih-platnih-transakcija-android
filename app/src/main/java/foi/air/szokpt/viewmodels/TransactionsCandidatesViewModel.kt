package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.transactions.SelectedTransactions
import hr.foi.air.szokpt.core.transactions.TransactionFilter
import hr.foi.air.szokpt.ws.models.TransactionPageResponse
import hr.foi.air.szokpt.ws.models.responses.SelectedTransaction
import hr.foi.air.szokpt.ws.models.responses.Transaction
import hr.foi.air.szokpt.ws.request_handlers.AddSelectedTransactions
import hr.foi.air.szokpt.ws.request_handlers.GetSelectedTransactionsRequestHandler
import hr.foi.air.szokpt.ws.request_handlers.GetTransactionsPageRequestHandler
import java.util.UUID

class TransactionsCandidatesViewModel : ViewModel() {
    private val _savedSelectedTransactions: MutableLiveData<List<SelectedTransaction>> =
        MutableLiveData(mutableListOf())
    private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData(emptyList())
    private val _selectedGuids: MutableLiveData<SelectedTransactions> = MutableLiveData()
    private val _transactionsFilter: MutableLiveData<TransactionFilter?> = MutableLiveData(null)
    private val _loading = MutableLiveData(true)

    val transactions: LiveData<List<Transaction>> = _transactions
    val selectedGuids: LiveData<SelectedTransactions> = _selectedGuids
    val transactionsFilter: LiveData<TransactionFilter?> = _transactionsFilter
    val loading: LiveData<Boolean> = _loading

    fun fetchTransactionPage() {
        val jwtToken = Auth.logedInUserData?.token ?: return

        val transactionsRequestHandler =
            GetTransactionsPageRequestHandler(jwtToken, null, _transactionsFilter.value)
        transactionsRequestHandler.sendRequest(object : ResponseListener<TransactionPageResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionPageResponse>) {
                _loading.value = true
                val transactionPage = response.data?.firstOrNull()
                _transactions.value = transactionPage?.transactions
                filterUnselectedTransactions()
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

    fun fetchSelectedTransactions() {
        val jwtToken = Auth.logedInUserData?.token ?: return

        val selectedTransactionsRequestHandler = GetSelectedTransactionsRequestHandler(jwtToken)
        selectedTransactionsRequestHandler.sendRequest(object :
            ResponseListener<SelectedTransaction> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<SelectedTransaction>) {
                _loading.value = true
                _savedSelectedTransactions.value = response.data.orEmpty().toMutableList()
                filterUnselectedTransactions()
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

    fun addSelectedTransactions() {
        val jwtToken = Auth.logedInUserData?.token ?: return

        val addSelectedTransactionsRequestHandler =
            AddSelectedTransactions(jwtToken, _selectedGuids.value!!)
        addSelectedTransactionsRequestHandler.sendRequest(object :
            ResponseListener<Unit> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Unit>) {
                _selectedGuids.value = SelectedTransactions(emptyList())
                fetchSelectedTransactions()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                println("Error receiving response: ${response.message}")
            }

            override fun onNetworkFailure(t: Throwable) {
                println("Error contacting network...")
            }
        })
    }

    private fun filterUnselectedTransactions() {
        val savedGuids = _savedSelectedTransactions.value?.map { it.guid }?.toSet().orEmpty()
        val filteredTransactions = _transactions.value?.filter { transaction ->
            transaction.guid !in savedGuids
        } ?: emptyList()
        _transactions.value = filteredTransactions
    }

    fun toggleSelectAllTransactions(pageGuids: Set<UUID>) {
        val currentSelectedGuids = _selectedGuids.value?.transactions.orEmpty().toSet()
        val updatedGuids = if (pageGuids.all { it in currentSelectedGuids }) {
            currentSelectedGuids - pageGuids
        } else {
            currentSelectedGuids + pageGuids
        }
        _selectedGuids.value = SelectedTransactions(updatedGuids.toList())
    }

    fun updateSelectionStatus(transactionId: UUID, isSelected: Boolean) {
        val currentTransactions = _selectedGuids.value?.transactions.orEmpty()
        val updatedTransactions = if (isSelected) {
            currentTransactions + transactionId
        } else {
            currentTransactions - transactionId
        }
        _selectedGuids.value = SelectedTransactions(transactions = updatedTransactions)
    }

    fun setFilter(filter: TransactionFilter?) {
        _transactionsFilter.value = filter
    }
}