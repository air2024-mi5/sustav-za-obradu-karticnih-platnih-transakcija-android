package foi.air.szokpt.viewmodels

import androidx.lifecycle.ViewModel
import foi.air.szokpt.helpers.TransactionDetailsHandler
import hr.foi.air.core.transactions.TransactionData
import hr.foi.air.core.transactions.TransactionDetailsOutcomeListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TransactionDetailsViewModel : ViewModel() {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _transactionData = MutableStateFlow<TransactionData?>(null)
    val transactionData: StateFlow<TransactionData?> get() = _transactionData

    private val transactionDetailsHandler = TransactionDetailsHandler()

    fun fetchTransactionDetails(
        transactionId: Int,
    ) {
        transactionDetailsHandler.getTransactionDetails(transactionId, object :
            TransactionDetailsOutcomeListener {
            override fun onSuccessfulTransactionDetailsFetch(transactionData: TransactionData) {
                _transactionData.value = transactionData
                _errorMessage.value = null
            }

            override fun onFailedTransactionDetailsFetch(failureMessage: String) {
                _errorMessage.value = failureMessage
                _transactionData.value = null
            }
        })
    }
}