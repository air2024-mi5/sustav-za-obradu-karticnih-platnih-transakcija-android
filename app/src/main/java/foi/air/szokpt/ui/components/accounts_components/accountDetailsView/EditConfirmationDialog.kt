package foi.air.szokpt.ui.components.accounts_components.accountDetailsView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.success


@Composable
fun EditConfirmationDialog(
    openEditDialog: MutableState<Boolean>,
    name: String,
    lastName: String,
    onConfirm: () -> Unit
) {
    DialogComponent(
        onDismissRequest = { openEditDialog.value = false },
        onConfirmation = {
            openEditDialog.value = false
            onConfirm()
        },
        dialogTitle = "Change user data?",
        dialogText = "Are you sure you want to change ${name} ${lastName}'s data?",
        iconTop = Icons.Rounded.CheckCircle,
        highlightColor = success,
        containerColor = BGLevelTwo
    )
}