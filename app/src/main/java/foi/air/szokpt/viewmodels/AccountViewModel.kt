package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.AccountUpdateHandler
import foi.air.szokpt.utils.UserDataValidator
import hr.foi.air.szokpt.core.accounts.AccountUpdateOutcomeListener
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
        val validator = UserDataValidator()
        val validationResult = validator.validate(user)

        return if (!validationResult.isValid) {
            _message.value = validationResult.message!!
            false
        } else {
            _message.value = ""
            true
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

    fun updateBlockedStatus(newBlockedStatus: Boolean) {
        _currentUserAccountData.value =
            _currentUserAccountData.value?.copy(blocked = newBlockedStatus)
        _storedUserAccountData.value =
            _storedUserAccountData.value?.copy(blocked = newBlockedStatus)
    }

    fun clearMessage() {
        _message.value = ""
    }
}