package foi.air.szokpt.ui.components.accounts_components.accountView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.warning
import hr.foi.air.szokpt.ws.models.responses.User

@Composable
fun BlockAccountDialog(
    openBlockDialog: MutableState<Boolean>,
    user: User,
    onConfirm: () -> Unit,
) {
    val dialogTitle = if (user.blocked) "Unblock User Account" else "Block User Account"
    val dialogTextBlockedStatus = if (user.blocked) "UNBLOCK" else "BLOCK"
    DialogComponent(
        onConfirmation = {
            onConfirm()
            openBlockDialog.value = false
        },
        onDismissRequest = { openBlockDialog.value = false },
        dialogTitle = dialogTitle,
        dialogText = "Are you sure you want to $dialogTextBlockedStatus ${user.firstName} ${user.lastName}, ${user.username}? \n",
        iconTop = Icons.Rounded.Close,
        highlightColor = warning,
        containerColor = BGLevelTwo,
        titleColor = TextWhite,
    )
}