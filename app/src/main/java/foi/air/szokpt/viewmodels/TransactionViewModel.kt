package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.TransactionUpdateHandler
import foi.air.szokpt.utils.TransactionDataValidator
import hr.foi.air.szokpt.core.transactions.TransactionUpdateOutcomeListener
import hr.foi.air.szokpt.ws.models.responses.Transaction

class TransactionViewModel() : ViewModel() {
    private val _storedTransactionData : MutableLiveData<Transaction> = MutableLiveData()
    private val _currentTransactionData : MutableLiveData<Transaction> = MutableLiveData()
    private val _message : MutableLiveData<String> = MutableLiveData("")

    val storedTransactionData : LiveData<Transaction> = _storedTransactionData
    val currentTransactionData : LiveData<Transaction> = _currentTransactionData
    val message : LiveData<String> = _message

    fun initializeTransactionData(transaction: Transaction) {
        _storedTransactionData.value = transaction
        _currentTransactionData.value = transaction
    }

    fun updateTransactionData(
        transactionUpdateHandler: TransactionUpdateHandler,
        newTransactionData: Transaction
    ) {
        val jwtToken = Auth.logedInUserData?.token
        if(jwtToken == null) {
            _message.value = "Something went wrong! Please try logging in again!"
            return
        }

        transactionUpdateHandler.update(
            jwtToken,
            newTransactionData,
            object: TransactionUpdateOutcomeListener {
            override fun onSuccessfulTransactionUpdate(successMessage: String) {
                updateView()
            }

            override fun onFailedTransactionUpdate(failureMessage: String) {
                _message.value = failureMessage
            }
        })
    }

    fun validateData(transaction: Transaction) : Boolean{
        val validator = TransactionDataValidator()
        val validationResult = validator.validate(transaction)

        return if(!validationResult.isValid) {
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

    fun resetTransactionData() {
        _currentTransactionData.value = _storedTransactionData.value
    }

    fun updateAmount(amount: Double) {
        _currentTransactionData.value = _currentTransactionData.value?.copy(amount = amount)
    }

    fun updateTimestamp(timestamp: String) {
        _currentTransactionData.value = _currentTransactionData.value?.copy(transactionTimestamp = timestamp)
    }

    fun clearMessage() {
        _message.value = ""
    }

}