package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.AccountUpdateHandler
import hr.foi.air.core.accounts.AccountUpdateOutcomeListener
import hr.foi.air.szokpt.ws.models.responses.User

class AccountViewModel : ViewModel() {
    private val _storedUserAccountData: MutableLiveData<User> = MutableLiveData()
    private val _currentUserAccountData: MutableLiveData<User> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData("")

    val storedUserAccountData: LiveData<User> = _storedUserAccountData
    val currentUserAccountData: LiveData<User> = _currentUserAccountData
    val message: LiveData<String> = _message

    fun initializeUserAccountData(user: User) {
        _storedUserAccountData.value = user.copy(password = "")
        _currentUserAccountData.value = _storedUserAccountData.value
    }

    fun updateAccountData(
        accountUpdateHandler: AccountUpdateHandler,
        newUserData: User,
    ) {
        val jwtToken = Auth.logedInUserData?.token
        if (jwtToken == null) {
            resetUserAccountData()
            _message.value = "Something went wrong! Please try logging in again!"
            return
        }
        accountUpdateHandler.update(
            jwtToken,
            newUserData,
            object : AccountUpdateOutcomeListener {
                override fun onSuccessfulAccountUpdate(successMessage: String) {
                    updateView()
                }

                override fun onFailedAccountUpdate(failureMessage: String) {
                    resetUserAccountData()
                    _message.value = failureMessage
                }
            })
    }

    fun validateData(user: User): Boolean {
        return when {
            user.username.isBlank() || user.firstName.isBlank() ||
                    user.lastName.isBlank() || user.email.isBlank() -> {
                _message.value = "All fields must be filled!"
                false
            }

            user.password.length in 1..2 -> {
                _message.value = "Password must contain at least 3 characters."
                false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches() -> {
                _message.value = "The email must be in the correct format."
                false
            }

            else -> {
                _message.value = ""
                true
            }
        }
    }


    private fun updateView() {
        _storedUserAccountData.value = currentUserAccountData.value?.copy(password = "")
        _currentUserAccountData.value = _storedUserAccountData.value
    }

    fun resetUserAccountData() {
        _currentUserAccountData.value = _storedUserAccountData.value
    }

    fun updateUsername(newUsername: String) {
        _currentUserAccountData.value = _currentUserAccountData.value?.copy(username = newUsername)
    }

    fun updatePassword(newPassword: String) {
        _currentUserAccountData.value = _currentUserAccountData.value?.copy(password = newPassword)
    }

    fun updateFirstName(newFirstName: String) {
        _currentUserAccountData.value =
            _currentUserAccountData.value?.copy(firstName = newFirstName)
    }

    fun updateLastName(newLastName: String) {
        _currentUserAccountData.value = _currentUserAccountData.value?.copy(lastName = newLastName)
    }

    fun updateEmail(newEmail: String) {
        _currentUserAccountData.value = _currentUserAccountData.value?.copy(email = newEmail)
    }

    fun clearMessage() {
        _message.value = ""
    }

    fun setMessage(newMessage: String) {
        _message.value = newMessage
    }
}