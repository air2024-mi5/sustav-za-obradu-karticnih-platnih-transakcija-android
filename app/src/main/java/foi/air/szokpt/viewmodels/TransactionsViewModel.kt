package foi.air.szokpt.viewmodels

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
    private val _pieChartData = MutableStateFlow(listOf<PieChartModel>())
    val pieChartData: StateFlow<List<PieChartModel>> get() = _pieChartData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val transactionsHandler = TransactionsSuccessHandler()

    fun fetchTransactions() {
        _errorMessage.value = null

        transactionsHandler.getTransactionsSuccess(object :
            TransactionsSuccessOutcomeListener {
            override fun onSuccessfulTransactionsFetch(
                totalTransactions: Int,
                successfulTransactions: Int,
                rejectedTransactions: Int,
                canceledTransactions: Int
            ) {
                val total = (successfulTransactions + canceledTransactions + rejectedTransactions).toFloat()
                val data = listOf(
                    PieChartModel("Successful",successfulTransactions, (successfulTransactions.toFloat() / total) * 100, success),
                    PieChartModel("Canceled", canceledTransactions,(canceledTransactions / total) * 100, danger),
                    PieChartModel("Rejected", rejectedTransactions, (rejectedTransactions / total) * 100, TextGray)
                )
                _pieChartData.value = data
            }

            override fun onFailedTransactionsFetch(failureMessage: String) {
                _errorMessage.value = failureMessage
            }
        })
    }
}

data class PieChartModel(
    val label: String,
    val value: Int,
    val scaledValue: Float,
    val color: Color,
)
