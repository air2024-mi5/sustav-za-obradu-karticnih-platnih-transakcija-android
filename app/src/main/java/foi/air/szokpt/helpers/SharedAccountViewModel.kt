package foi.air.szokpt.helpers

import androidx.lifecycle.ViewModel
import foi.air.szokpt.models.ListedAccountInformation

class SharedAccountViewModel : ViewModel() {
    var selectedAccount: ListedAccountInformation? = null
}