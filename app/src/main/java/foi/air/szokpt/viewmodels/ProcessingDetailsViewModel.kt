package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.LatestProcessingHandler
import foi.air.szokpt.models.ProcessingRecord
import hr.foi.air.szokpt.core.processing.BatchRecord
import hr.foi.air.szokpt.core.processing.LatestProcessingOutcomeListener

class ProcessingDetailsViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _latestProcessing = MutableLiveData<ProcessingRecord?>(null)
    val latestProcessing: MutableLiveData<ProcessingRecord?> get() = _latestProcessing

    private val latestProcessingHandler = LatestProcessingHandler()
    private val jwtToken = Auth.logedInUserData?.token

    fun fetchLatestProcessing() {
        if (jwtToken != null) {
            latestProcessingHandler.getLatestProcessing(
                jwtToken,
                object : LatestProcessingOutcomeListener {
                    override fun onSuccessfulLatestProcessingFetch(
                        status: String,
                        scheduledAt: String,
                        processedAt: String,
                        batchRecords: List<BatchRecord>,
                        processedTransactionsCount: Int
                    ) {
                        _latestProcessing.value = ProcessingRecord(
                            status = status,
                            scheduledAt = scheduledAt,
                            processedAt = processedAt,
                            batchRecords = batchRecords,
                            processedTransactionsCount = processedTransactionsCount
                        )
                        _errorMessage.value = null
                    }

                    override fun onFailedLatestProcessingFetch(failureMessage: String) {
                        _errorMessage.value = failureMessage
                    }
                })
        }
    }
}