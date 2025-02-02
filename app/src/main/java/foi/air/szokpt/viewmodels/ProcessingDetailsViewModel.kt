package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.handlers.LatestProcessingHandler
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.core.processing.BatchRecord
import hr.foi.air.szokpt.core.processing.LatestProcessingOutcomeListener
import hr.foi.air.szokpt.core.processing.ProcessingRecord
import hr.foi.air.szokpt.ws.request_handlers.RevertLastProcessingRequestHandler

class ProcessingDetailsViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _processingRecord = MutableLiveData<ProcessingRecord?>(null)
    val processingRecord: MutableLiveData<ProcessingRecord?> get() = _processingRecord

    fun fetchLatestProcessing() {
        val jwtToken = setJwt() ?: return

        val latestProcessingHandler = LatestProcessingHandler()
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
                    _processingRecord.value = ProcessingRecord(
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

    fun revertLastProcessing() {
        val jwtToken = setJwt() ?: return

        val revertLastProcessingRequestHandler = RevertLastProcessingRequestHandler(jwtToken)
        revertLastProcessingRequestHandler.sendRequest(object :
            ResponseListener<Unit> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Unit>) {
                _errorMessage.value = ""
                _processingRecord.value = _processingRecord.value?.copy(status = "REVERTED")
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                showErrorMessage("Something went wrong! Please try again!")
            }

            override fun onNetworkFailure(t: Throwable) {
                showErrorMessage("Please check your internet connection!")
            }
        })
    }

    private fun setJwt(): String? {
        val jwtToken = Auth.logedInUserData?.token
        if (jwtToken == null) {
            showErrorMessage("Something went wrong! Please try logging in again!")
        }
        return jwtToken
    }

    private fun showErrorMessage(message: String) {
        _errorMessage.value = message
    }
}
