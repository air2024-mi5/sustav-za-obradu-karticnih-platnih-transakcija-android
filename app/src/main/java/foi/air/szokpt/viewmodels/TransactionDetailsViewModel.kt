package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.helpers.TransactionDetailsHandler
import hr.foi.air.core.transactions.TransactionData
import hr.foi.air.core.transactions.TransactionDetailsOutcomeListener

class TransactionDetailsViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _transactionData = MutableLiveData<TransactionData?>(null)
    val transactionData: LiveData<TransactionData?> get() = _transactionData

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