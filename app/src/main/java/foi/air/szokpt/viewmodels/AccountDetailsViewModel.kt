package foi.air.szokpt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.szokpt.ws.models.responses.User

class AccountDetailsViewModel : ViewModel() {
    private val _providedUserAccount: MutableLiveData<User> = MutableLiveData()
    val providedUserAccount: MutableLiveData<User> = _providedUserAccount

    private val _currentUserAccountData: MutableLiveData<User> = MutableLiveData()
    val currentUserAccountData: MutableLiveData<User> = _currentUserAccountData


    fun initializeUserAccountData(user: User) {
        _providedUserAccount.value = user
        _currentUserAccountData.value = _providedUserAccount.value?.copy(password = "")
    }

    fun updateAccountData() {

    }


    fun resetUserAccountData() {
        _providedUserAccount.value = currentUserAccountData.value
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
}