package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.handlers.TransactionDetailsHandler
import foi.air.szokpt.handlers.TransactionUpdateHandler
import foi.air.szokpt.utils.TransactionDataValidator
import hr.foi.air.szokpt.core.transactions.TransactionData
import hr.foi.air.szokpt.core.transactions.TransactionDetailsOutcomeListener
import hr.foi.air.szokpt.core.transactions.TransactionUpdateOutcomeListener
import java.util.UUID

class TransactionViewModel() : ViewModel() {
    private val _storedTransactionData: MutableLiveData<TransactionData?> = MutableLiveData()
    private val _currentTransactionData: MutableLiveData<TransactionData?> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData("")

    val storedTransactionData: LiveData<TransactionData?> = _storedTransactionData
    val currentTransactionData: LiveData<TransactionData?> = _currentTransactionData
    val message: LiveData<String> = _message

    fun initializeTransactionData(transactionGuid: UUID) {
        val jwtToken = Auth.logedInUserData?.token ?: return
        val transactionDetailsHandler = TransactionDetailsHandler()

        transactionDetailsHandler.getTransactionDetails(
            jwtToken,
            transactionGuid,
            object : TransactionDetailsOutcomeListener {
                override fun onSuccessfulTransactionDetailsFetch(transactionData: TransactionData) {
                    _storedTransactionData.value = transactionData
                    _currentTransactionData.value = _storedTransactionData.value
                    _message.value = ""
                }

                override fun onFailedTransactionDetailsFetch(failureMessage: String) {
                    _message.value = failureMessage
                    _storedTransactionData.value = null
                    _currentTransactionData.value = null
                }
            })
    }

    fun updateTransactionData(
        transactionUpdateHandler: TransactionUpdateHandler,
        newTransactionData: TransactionData
    ) {
        val jwtToken = Auth.logedInUserData?.token
        if (jwtToken == null) {
            _message.value = "Something went wrong! Please try logging in again!"
            return
        }

        transactionUpdateHandler.update(
            jwtToken,
            newTransactionData,
            object : TransactionUpdateOutcomeListener {
                override fun onSuccessfulTransactionUpdate(successMessage: String) {
                    updateView()
                }

                override fun onFailedTransactionUpdate(failureMessage: String) {
                    _message.value = failureMessage
                }
            })
    }

    fun validateData(transaction: TransactionData): Boolean {
        val validator = TransactionDataValidator()
        val validationResult = validator.validate(transaction)

        return if (!validationResult.isValid) {
            _message.value = validationResult.message!!
            false
        } else {
            _message.value = ""
            true
        }
    }

    private fun updateView() {
        _storedTransactionData.value = _currentTransactionData.value
    }

    fun updateAmount(amount: Double) {
        _currentTransactionData.value = _currentTransactionData.value?.copy(amount = amount)
    }

    fun updateTimestamp(timestamp: String) {
        _currentTransactionData.value =
            _currentTransactionData.value?.copy(transactionTimestamp = timestamp)
    }

    fun setMessage(message: String) {
        _message.value = message
    }
}