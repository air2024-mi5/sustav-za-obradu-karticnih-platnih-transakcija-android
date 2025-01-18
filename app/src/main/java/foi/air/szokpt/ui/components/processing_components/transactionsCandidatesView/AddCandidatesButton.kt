package foi.air.szokpt.ui.components.processing_components.transactionsCandidatesView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.success
import java.util.UUID

@Composable
fun AddCandidatesButton(
    selectedGuids: List<UUID>?,
    onAdd: () -> Unit,
) {
    val buttonColor = if (selectedGuids.isNullOrEmpty()) {
        Color(0xFF003300)
    } else {
        success
    }

    val isEnabled = !selectedGuids.isNullOrEmpty()

    OutlineBouncingButton(
        inputText = "Confirm selection",
        inputIcon = Icons.Rounded.CheckCircle,
        contentColor = buttonColor,
        borderColor = buttonColor,
        onClick = {
            if (isEnabled)
                onAdd()
        }
    )
}