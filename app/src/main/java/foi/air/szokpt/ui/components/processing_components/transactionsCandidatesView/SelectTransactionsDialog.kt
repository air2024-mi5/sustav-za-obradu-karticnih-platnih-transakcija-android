package foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.warning

@Composable
fun SelectTransactionsDialog(
    openAddCandidatesDialog: MutableState<Boolean>,
    onConfirm: () -> Unit,
) {
    val dialogTitle = "Confirm Selected Transactions"
    DialogComponent(
        onConfirmation = {
            onConfirm()
            openAddCandidatesDialog.value = false
        },
        onDismissRequest = { openAddCandidatesDialog.value = false },
        dialogTitle = dialogTitle,
        dialogText = "Are you sure you want to confirm the selection for daily processing?\n",
        iconTop = Icons.Rounded.Check,
        highlightColor = warning,
        containerColor = BGLevelTwo,
        titleColor = TextWhite,
    )
}