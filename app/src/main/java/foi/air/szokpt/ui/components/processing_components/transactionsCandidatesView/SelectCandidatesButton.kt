package foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.success

@Composable
fun SelectCandidatesButton() {
    OutlineBouncingButton(
        onClick = {},
        inputText = "Confirm selection",
        inputIcon = Icons.Rounded.CheckCircle,
        contentColor = success,
        borderColor = success
    )
}