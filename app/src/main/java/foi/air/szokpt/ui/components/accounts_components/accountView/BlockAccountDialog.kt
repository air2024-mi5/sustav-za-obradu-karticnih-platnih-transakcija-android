package foi.air.szokpt.ui.components.accounts_components.accountView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.warning
import hr.foi.air.szokpt.ws.models.responses.User

@Composable
fun BlockAccountDialog(
    openBlockDialog: MutableState<Boolean>,
    user: User
) {
    DialogComponent(
        onDismissRequest = { openBlockDialog.value = false },
        onConfirmation = { openBlockDialog.value = false },
        dialogTitle = "Block User Account",
        dialogText = "Are you sure you want to BLOCK ${user.firstName} ${user.lastName}, @${user.username}? \n \nBe cautious!",
        iconTop = Icons.Rounded.Close,
        highlightColor = warning,
        containerColor = BGLevelTwo
    )
}