package foi.air.szokpt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.TransactionDetailsHandler
import hr.foi.air.szokpt.core.transactions.TransactionData
import hr.foi.air.szokpt.core.transactions.TransactionDetailsOutcomeListener
import java.util.UUID

class TransactionDetailsViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: MutableLiveData<String?> get() = _errorMessage

    private val _transactionData = MutableLiveData<TransactionData?>(null)
    val transactionData: MutableLiveData<TransactionData?> get() = _transactionData

    private val transactionDetailsHandler = TransactionDetailsHandler()

    fun fetchTransactionDetails(
        transactionGuid: UUID,
    ) {
        val jwtToken = Auth.logedInUserData?.token ?: return

        transactionDetailsHandler.getTransactionDetails(
            jwtToken,
            transactionGuid,
            object : TransactionDetailsOutcomeListener {
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