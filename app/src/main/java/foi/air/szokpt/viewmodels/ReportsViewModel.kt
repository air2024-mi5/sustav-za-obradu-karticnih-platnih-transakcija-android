package foi.air.szokpt.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.handlers.TransactionsSuccessHandler
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
    private val jwtToken = Auth.logedInUserData?.token

    fun fetchTransactionsSuccess() {
        _errorMessage.value = null

        if (jwtToken != null) {
            transactionsHandler.getTransactionsSuccess(jwtToken, object :
                TransactionsSuccessOutcomeListener {
                override fun onSuccessfulTransactionsSuccessFetch(
                    totalTransactions: Int,
                    successfulTransactions: Int,
                    rejectedTransactions: Int,
                    canceledTransactions: Int
                ) {
                    if (totalTransactions == 0) {
                        _errorMessage.value =
                            "No transactions were made, so there are no statistical data."
                    } else {
                        _errorMessage.value = null
                        val data: List<PieChartModel> = listOf(
                            PieChartModel(
                                "Successful",
                                successfulTransactions,
                                (successfulTransactions.toFloat() / totalTransactions.toFloat()) * 100,
                                success
                            ),
                            PieChartModel(
                                "Canceled",
                                canceledTransactions,
                                (canceledTransactions.toFloat() / totalTransactions.toFloat()) * 100,
                                danger
                            ),
                            PieChartModel(
                                "Rejected",
                                rejectedTransactions,
                                (rejectedTransactions.toFloat() / totalTransactions.toFloat()) * 100,
                                TextGray
                            )
                        )
                        _pieChartData.value = data
                    }
                }

                override fun onFailedTransactionsSuccessFetch(failureMessage: String) {
                    _errorMessage.value = failureMessage
                }
            })
        }
    }
}

data class PieChartModel(
    val label: String,
    val value: Int,
    val scaledValue: Float,
    val color: Color,
)
