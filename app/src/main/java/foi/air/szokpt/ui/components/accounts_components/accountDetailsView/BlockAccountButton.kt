package foi.air.szokpt.ui.components.accounts_components.accountDetailsView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.warning

@Composable
fun BlockAccountButton(openBlockDialog: MutableState<Boolean>) {
    OutlineBouncingButton(
        onClick = { openBlockDialog.value = true },
        inputText = "Block",
        contentColor = warning,
        borderColor = warning,
        inputIcon = Icons.Rounded.Clear
    )
}