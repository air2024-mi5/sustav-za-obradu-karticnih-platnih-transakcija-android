package foi.air.szokpt.ui.components.accounts_components.accountDetailsView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.danger


@Composable
fun DeactivateAccountDialog(
    openDeactivateDialog: MutableState<Boolean>,
    name: String,
    lastName: String,
    username: String
) {
    DialogComponent(
        onDismissRequest = { openDeactivateDialog.value = false },
        onConfirmation = { openDeactivateDialog.value = false },
        dialogTitle = "Deactivate User Account",
        dialogText = "Are you sure you want to DEACTIVATE ${name} ${lastName}, @${username}? \n \nBe cautious!",
        iconTop = Icons.Rounded.Delete,
        highlightColor = danger,
        containerColor = BGLevelTwo
    )
}
