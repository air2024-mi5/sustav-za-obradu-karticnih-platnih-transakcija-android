package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.TransactionsPerDayHandler
import hr.foi.air.szokpt.core.reports.TransactionsPerDayOutcomeListener
import java.sql.Timestamp

class TransactionsPerDayViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _totalTransactions = MutableLiveData(0)
    val totalTransactions: LiveData<Int?> get() = _totalTransactions

    private val _transactionsPerDay = MutableLiveData<Map<Timestamp, Int>?>(emptyMap())
    val transactionsPerDay: MutableLiveData<Map<Timestamp, Int>?> get() = _transactionsPerDay

    private val transactionsPerDayHandler = TransactionsPerDayHandler()
    private val jwtToken = Auth.logedInUserData?.token

    fun fetchTransactionsPerDay() {
        if (jwtToken != null) {
            transactionsPerDayHandler.getTransactionsPerDay(jwtToken,
                object : TransactionsPerDayOutcomeListener {
                    override fun onSuccessfulTransactionsPerDayFetch(
                        totalTransactions: Int,
                        transactionsPerDay: Map<Timestamp, Int>
                    ) {
                        _totalTransactions.value = totalTransactions
                        if (totalTransactions == 0) {
                            _errorMessage.value =
                                "No transactions were made, so there are no statistical data."
                            _transactionsPerDay.value = emptyMap()
                        } else {
                            _errorMessage.value = null
                            _transactionsPerDay.value = transactionsPerDay
                        }
                    }

                    override fun onFailedTransactionsPerDayFetch(failureMessage: String) {
                        _errorMessage.value = failureMessage
                        _totalTransactions.value = 0
                        _transactionsPerDay.value = emptyMap()
                    }
                })
        }
    }
}