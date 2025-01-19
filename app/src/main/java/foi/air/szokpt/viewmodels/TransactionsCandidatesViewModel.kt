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
import hr.foi.air.szokpt.ws.request_handlers.AddSelectedTransactionsRequestHandler
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
    private val _message: MutableLiveData<String> = MutableLiveData("")
    private val _toastMessage: MutableLiveData<String> = MutableLiveData("")
    private val _showToast = MutableLiveData(false)

    val transactions: LiveData<List<Transaction>> = _transactions
    val selectedGuids: LiveData<SelectedTransactions> = _selectedGuids
    val transactionsFilter: LiveData<TransactionFilter?> = _transactionsFilter
    val loading: LiveData<Boolean> = _loading
    val message: LiveData<String> = _message
    val toastMessage: LiveData<String> = _toastMessage
    val showToast: LiveData<Boolean> = _showToast

    fun fetchTransactionPage() {
        val jwtToken = getJwtTokenOrShowError() ?: return

        val transactionsRequestHandler =
            GetTransactionsPageRequestHandler(jwtToken, null, _transactionsFilter.value)
        transactionsRequestHandler.sendRequest(object : ResponseListener<TransactionPageResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<TransactionPageResponse>) {
                _loading.value = true
                _message.value = ""
                val transactionPage = response.data?.firstOrNull()
                _transactions.value = transactionPage?.transactions
                filterUnselectedTransactions()
                _loading.value = false
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                showErrorMessage("Something went wrong! Please try again!")
            }

            override fun onNetworkFailure(t: Throwable) {
                showErrorMessage("Please check your internet connection!")
            }
        })
    }

    fun fetchSelectedTransactions() {
        val jwtToken = getJwtTokenOrShowError() ?: return

        val selectedTransactionsRequestHandler = GetSelectedTransactionsRequestHandler(jwtToken)
        selectedTransactionsRequestHandler.sendRequest(object :
            ResponseListener<SelectedTransaction> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<SelectedTransaction>) {
                _message.value = ""
                _savedSelectedTransactions.value = response.data.orEmpty().toMutableList()
                filterUnselectedTransactions()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                showErrorMessage("Please check your internet connection!")
            }

            override fun onNetworkFailure(t: Throwable) {
                showErrorMessage("Please check your internet connection!")
            }
        })
    }

    fun addSelectedTransactions() {
        val jwtToken = getJwtTokenOrShowError() ?: return

        val addSelectedTransactionsRequestHandler =
            AddSelectedTransactionsRequestHandler(jwtToken, _selectedGuids.value!!)
        addSelectedTransactionsRequestHandler.sendRequest(object :
            ResponseListener<Unit> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Unit>) {
                _loading.value = true
                _selectedGuids.value = SelectedTransactions(emptyList())
                fetchSelectedTransactions()
                _loading.value = false
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                _toastMessage.value = "Something went wrong while submitting!"
                _showToast.value = true
                _loading.value = false
            }

            override fun onNetworkFailure(t: Throwable) {
                _toastMessage.value = "Please check your internet connection!"
                _showToast.value = true
                _loading.value = false
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

    private fun getJwtTokenOrShowError(): String? {
        val jwtToken = Auth.logedInUserData?.token
        if (jwtToken == null) {
            showErrorMessage("Something went wrong! Please try logging in again!")
        }
        return jwtToken
    }

    private fun showErrorMessage(message: String) {
        _message.value = message
        _loading.value = false
    }

    fun resetToast() {
        _showToast.value = false
    }
}