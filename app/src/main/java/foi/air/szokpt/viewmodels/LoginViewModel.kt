package foi.air.szokpt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.LoginHandler
import hr.foi.air.core.login.LoginOutcomeListener
import hr.foi.air.core.login.LoginBody
import hr.foi.air.core.login.LoginUserData

class LoginViewModel() : ViewModel() {
    val username: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: MutableLiveData<String> = _errorMessage

    fun login(
        loginHandler: LoginHandler,
        loginBody: LoginBody,
        onSuccessfulLogin:() -> Unit,
        onFailedLogin:() -> Unit
    ) {
        loginHandler.login(loginBody, object: LoginOutcomeListener {
            override fun onSuccessfulLogin(loginUserData: LoginUserData) {
                Auth.logedInUserData = loginUserData
                onSuccessfulLogin()
            }

            override fun onFailedLogin(reason: String) {
                _errorMessage.value = reason
                onFailedLogin()
            }

        })
    }
}