package foi.air.szokpt.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import foi.air.szokpt.helpers.TransactionsSuccessHandler
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import hr.foi.air.szokpt.core.transactions.TransactionsSuccessOutcomeListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReportsViewModel : ViewModel() {
    private val _pieChartData = MutableStateFlow(listOf<PieChartModel>())
    val pieChartData: StateFlow<List<PieChartModel>> get() = _pieChartData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val transactionsHandler = TransactionsSuccessHandler()

    fun fetchTransactionsSuccess() {
        _errorMessage.value = null

        transactionsHandler.getTransactionsSuccess(object :
            TransactionsSuccessOutcomeListener {
            override fun onSuccessfulTransactionsSuccessFetch(
                totalTransactions: Int,
                successfulTransactions: Int,
                rejectedTransactions: Int,
                canceledTransactions: Int
            ) {
                val total = (successfulTransactions + canceledTransactions + rejectedTransactions).toFloat()
                val data: List<PieChartModel> = if (total == 0f) {
                    listOf(
                        PieChartModel("Successful", 0, 0f, success),
                        PieChartModel("Canceled", 0, 0f, danger),
                        PieChartModel("Rejected", 0, 0f, TextGray)
                    )
                } else {
                    listOf(
                        PieChartModel("Successful", successfulTransactions, (successfulTransactions.toFloat() / total) * 100, success),
                        PieChartModel("Canceled", canceledTransactions, (canceledTransactions.toFloat() / total) * 100, danger),
                        PieChartModel("Rejected", rejectedTransactions, (rejectedTransactions.toFloat() / total) * 100, TextGray)
                    )
                }
                _pieChartData.value = data
            }

            override fun onFailedTransactionsSuccessFetch(failureMessage: String) {
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
