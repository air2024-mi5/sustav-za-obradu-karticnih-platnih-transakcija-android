package foi.air.szokpt.ui.components.accounts_components.accountView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.danger

@Composable
fun DeactivateAccountButton(openDeactivateDialog: MutableState<Boolean>) {
    OutlineBouncingButton(
        onClick = { openDeactivateDialog.value = true },
        inputText = "Deactivate Acc.",
        contentColor = danger,
        borderColor = danger,
        inputIcon = Icons.Rounded.Delete
    )
}