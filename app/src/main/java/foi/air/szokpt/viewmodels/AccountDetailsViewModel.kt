package foi.air.szokpt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.szokpt.ws.models.responses.User
import hr.foi.air.szokpt.ws.models.responses.UserRole

class AccountDetailsViewModel : ViewModel() {
    private val _username: MutableLiveData<String> = MutableLiveData()
    private val _password: MutableLiveData<String> = MutableLiveData()
    private val _firstName: MutableLiveData<String> = MutableLiveData()
    private val _lastName: MutableLiveData<String> = MutableLiveData()
    private val _email: MutableLiveData<String> = MutableLiveData()
    private val _role: MutableLiveData<UserRole> = MutableLiveData()

    val username: LiveData<String> = _username
    val password: LiveData<String> = _password
    val firstName: LiveData<String> = _firstName
    val lastName: LiveData<String> = _lastName
    val email: LiveData<String> = _email
    val role: MutableLiveData<UserRole> = _role


    fun initializeUserData(user: User) {
        _username.value = user.username
        _password.value = user.password
        _firstName.value = user.firstName
        _lastName.value = user.lastName
        _email.value = user.email
        _role.value = user.role
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateFirstName(newFirstName: String) {
        _firstName.value = newFirstName
    }

    fun updateLastName(newLastName: String) {
        _lastName.value = newLastName
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }
}