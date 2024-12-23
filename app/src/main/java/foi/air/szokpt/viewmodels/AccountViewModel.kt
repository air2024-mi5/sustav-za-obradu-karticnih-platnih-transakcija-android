package foi.air.szokpt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import foi.air.szokpt.context.Auth
import foi.air.szokpt.helpers.AccountUpdateHandler
import hr.foi.air.core.accounts.AccountUpdateOutcomeListener
import hr.foi.air.szokpt.ws.models.responses.User

class AccountViewModel : ViewModel() {
    private val _originalUserAccount: MutableLiveData<User> = MutableLiveData()
    private val _currentUserAccountData: MutableLiveData<User> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData("")

    val storedUserAccountData: MutableLiveData<User> = _originalUserAccount
    val currentUserAccountData: MutableLiveData<User> = _currentUserAccountData
    val message: MutableLiveData<String> = _message

    fun initializeUserAccountData(user: User) {
        _originalUserAccount.value = user
        _currentUserAccountData.value = _originalUserAccount.value?.copy(password = "")
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
                    _message.value = "Something went wrong! Please try again!"
                }
            })
    }


    private fun updateView() {
        _originalUserAccount.value = _currentUserAccountData.value
    }

    fun resetUserAccountData() {
        _currentUserAccountData.value = _originalUserAccount.value
        _currentUserAccountData.value = _originalUserAccount.value?.copy(password = "")
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
}