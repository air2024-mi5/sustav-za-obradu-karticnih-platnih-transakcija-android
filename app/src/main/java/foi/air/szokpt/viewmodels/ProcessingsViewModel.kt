package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import hr.foi.air.szokpt.core.network.ResponseListener
import hr.foi.air.szokpt.core.network.models.ErrorResponseBody
import hr.foi.air.szokpt.core.network.models.SuccessfulResponseBody
import hr.foi.air.szokpt.ws.models.ProcessingPageResponse
import hr.foi.air.szokpt.ws.request_handlers.ProcessingsRequestHandler

class ProcessingsViewModel() : ViewModel() {
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _processingPage: MutableLiveData<ProcessingPageResponse?> = MutableLiveData(null)
    val processingPage: LiveData<ProcessingPageResponse?> = _processingPage

    private val _currentPage: MutableLiveData<Int?> = MutableLiveData(1)
    val currentPage: LiveData<Int?> = _currentPage

    private val _totalPages: MutableLiveData<Int?> = MutableLiveData(1)
    val totalPages: LiveData<Int?> = _totalPages

    fun fetchProcessings(page: Int) {
        val jwtToken = Auth.logedInUserData?.token ?: return

        val processingsRequestHandler = ProcessingsRequestHandler(page, jwtToken)

        processingsRequestHandler.sendRequest(object : ResponseListener<ProcessingPageResponse> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<ProcessingPageResponse>) {
                _isLoading.value = true
                _processingPage.value = response.data?.first()
                _currentPage.value = _processingPage.value?.currentPage
                _totalPages.value = _processingPage.value?.totalPages
                _errorMessage.value = null
                _isLoading.value = false
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                _errorMessage.value = "Error receiving response: ${response.error_message}"
                _processingPage.value = null
            }

            override fun onNetworkFailure(t: Throwable) {
                _errorMessage.value = "Network error: ${t.message}"
                _processingPage.value = null
            }
        })
    }
}