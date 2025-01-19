package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.LatestProcessingHandler
import hr.foi.air.szokpt.core.processing.BatchRecord
import hr.foi.air.szokpt.core.processing.LatestProcessingOutcomeListener
import java.time.LocalDateTime

class LatestProcessingViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _status = MutableLiveData<String?>(null)
    val status: LiveData<String?> get() = _status

    private val _scheduledAt = MutableLiveData<LocalDateTime?>(null)
    val scheduledAt: LiveData<LocalDateTime?> get() = _scheduledAt

    private val _processedAt = MutableLiveData<LocalDateTime?>(null)
    val processedAt: LiveData<LocalDateTime?> get() = _processedAt

    private val _batchRecords = MutableLiveData<List<BatchRecord>?>(null)
    val batchRecords: LiveData<List<BatchRecord>?> get() = _batchRecords

    private val _processedTransactionsCount = MutableLiveData<Int?>(null)
    val processedTransactionsCount: MutableLiveData<Int?> get() = _processedTransactionsCount

    private val latestProcessingHandler = LatestProcessingHandler()
    private val jwtToken = Auth.logedInUserData?.token

    fun fetchLatestProcessing() {
        if (jwtToken != null) {
            latestProcessingHandler.getLatestProcessing(
                jwtToken,
                object : LatestProcessingOutcomeListener {
                    override fun onSuccessfulLatestProcessingFetch(
                        status: String,
                        scheduledAt: LocalDateTime,
                        processedAt: LocalDateTime,
                        batchRecords: List<BatchRecord>,
                        processedTransactionsCount: Int
                    ) {
                        _status.value = status
                        _scheduledAt.value = scheduledAt
                        _processedAt.value = processedAt
                        _batchRecords.value = batchRecords
                        _processedTransactionsCount.value = processedTransactionsCount
                        _errorMessage.value = null
                    }

                    override fun onFailedLatestProcessingFetch(failureMessage: String) {
                        _errorMessage.value = failureMessage
                        _status.value = null
                        _scheduledAt.value = null
                        _processedAt.value = null
                        _batchRecords.value = null
                        _processedTransactionsCount.value = null
                    }
                })
        }
    }
}