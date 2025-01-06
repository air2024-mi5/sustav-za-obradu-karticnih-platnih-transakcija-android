package foi.air.szokpt.ui.components.accounts_components.accountView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.warning
import hr.foi.air.szokpt.ws.models.responses.User

@Composable
fun BlockAccountButton(openBlockDialog: MutableState<Boolean>, user: User) {
    val buttonText = if (user.blocked) "Unblock" else "Block"
    OutlineBouncingButton(
        onClick = { openBlockDialog.value = true },
        inputText = buttonText,
        contentColor = warning,
        borderColor = warning,
        inputIcon = Icons.Rounded.Clear
    )
}