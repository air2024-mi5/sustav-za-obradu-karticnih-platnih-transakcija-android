package foi.air.szokpt.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.RegistrationHandler
import hr.foi.air.core.register.RegistrationBody
import hr.foi.air.core.register.RegistrationOutcomeListener
import hr.foi.air.core.register.Role

class RegistrationViewModel() : ViewModel() {
    val username: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val firstName: MutableLiveData<String> = MutableLiveData("")
    val lastName: MutableLiveData<String> = MutableLiveData("")
    val email: MutableLiveData<String> = MutableLiveData("")

    val role: MutableLiveData<Role> = MutableLiveData(Role("user"))

    private val _message: MutableLiveData<String> = MutableLiveData("")
    val message: MutableLiveData<String> = _message

    fun register(
        registrationHandler: RegistrationHandler,
        registrationBody: RegistrationBody,
        onSuccessfulRegistration:() -> Unit,
        onFailedRegistration:() -> Unit
    ) {
        try {
            val jwtToken = Auth.logedInUserData?.token
            if (jwtToken != null) {
                registrationHandler.register(
                    jwtToken,
                    registrationBody,
                    object : RegistrationOutcomeListener {
                        override fun onSuccessfulRegistration(successMessage: String) {
                            _message.value = successMessage
                            onSuccessfulRegistration()
                        }

                        override fun onFailedRegistration(failureMessage: String) {
                            _message.value = failureMessage
                            onFailedRegistration()
                        }
                    })
            } else {
                _message.value = "JWT token is missing"
            }
        } catch (e: Exception) {
            Log.e("RegistrationViewModelError", "Exception during registration: ${e.message}")
            _message.value = "Error occurred during registration."
            onFailedRegistration()
        }
    }
}