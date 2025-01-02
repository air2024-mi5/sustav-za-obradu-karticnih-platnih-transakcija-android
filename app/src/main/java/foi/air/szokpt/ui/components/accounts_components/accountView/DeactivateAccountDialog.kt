package foi.air.szokpt.ui.components.accounts_components.accountView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.danger
import hr.foi.air.szokpt.ws.models.responses.User


@Composable
fun DeactivateAccountDialog(
    openDeactivateDialog: MutableState<Boolean>,
    user: User
) {
    DialogComponent(
        onDismissRequest = { openDeactivateDialog.value = false },
        onConfirmation = { openDeactivateDialog.value = false },
        dialogTitle = "Deactivate User Account",
        dialogText = "Are you sure you want to DEACTIVATE ${user.firstName} ${user.lastName}, @${user.username}? \n \nBe cautious!",
        iconTop = Icons.Rounded.Delete,
        highlightColor = danger,
        containerColor = BGLevelTwo
    )
}
