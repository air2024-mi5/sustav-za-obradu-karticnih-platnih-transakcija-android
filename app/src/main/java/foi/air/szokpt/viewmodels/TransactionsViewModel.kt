package foi.air.szokpt.viewmodels

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import foi.air.szokpt.helpers.TransactionsSuccessHandler
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import hr.foi.air.core.transactions.TransactionsSuccessOutcomeListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TransactionsViewModel : ViewModel() {
    private val _barData = MutableStateFlow(listOf<BarData>())
    val barData: StateFlow<List<BarData>> get() = _barData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val transactionsHandler = TransactionsSuccessHandler()

    fun fetchTransactions() {
        _isLoading.value = true
        _errorMessage.value = null

        transactionsHandler.getTransactionsSuccess(object :
            TransactionsSuccessOutcomeListener {
            override fun onSuccessfulTransactionsFetch(
                totalTransactions: Int,
                successfulTransactions: Int,
                rejectedTransactions: Int,
                canceledTransactions: Int
            ) {
                _isLoading.value = false

                val maxHeight = 200
                val maxValue = listOf(
                    totalTransactions,
                    successfulTransactions,
                    rejectedTransactions,
                    canceledTransactions
                ).maxOrNull() ?: 1

                val data = listOf(
                    BarData("Successful", (successfulTransactions / maxValue.toFloat()) * maxHeight, success, successfulTransactions),
                    BarData("Canceled", (canceledTransactions / maxValue.toFloat()) * maxHeight, danger, canceledTransactions),
                    BarData("Rejected", (rejectedTransactions / maxValue.toFloat()) * maxHeight, TextGray, rejectedTransactions)
                )
                _barData.value = data
                Log.d("VM - ONSUCCESSFUL", "_barData.value je: " + _barData.value)
            }

            override fun onFailedTransactionsFetch(failureMessage: String) {
                _isLoading.value = false
                _errorMessage.value = failureMessage
                Log.d("VM - ONSFAILED", "failure message je: " + _errorMessage.value)
            }
        })
    }
}

data class BarData(
    val label: String,
    val height: Float,
    val color: Color,
    val value: Int
)