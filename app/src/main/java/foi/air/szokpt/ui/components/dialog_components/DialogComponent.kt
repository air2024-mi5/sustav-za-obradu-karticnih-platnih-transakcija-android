package foi.air.szokpt.ui.components.dialog_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import foi.air.szokpt.ui.components.interactible_components.FillBouncingButton
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.interactible_components.TextBouncingButton
import foi.air.szokpt.ui.theme.BGLevelThree
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.success

@Composable
fun DialogComponent(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    iconTop: ImageVector = Icons.Rounded.CheckCircle,
    highlightColor: Color = Primary,
    containerColor: Color = BGLevelThree,
    confirmationText: String = "Confirm",
    dismissText: String = "Dismiss",
    iconConfirm: ImageVector = Icons.Rounded.Check,
    iconDismiss: ImageVector = Icons.Rounded.Close,
) {
    AlertDialog(
        icon = {
            Icon(iconTop, contentDescription = "Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
             TextBouncingButton(
                inputText = confirmationText,
                inputIcon = iconConfirm,
                buttonColor = highlightColor,
                onClick = { onConfirmation() }
            )

        },
        dismissButton = {
            TextBouncingButton(
                inputText = dismissText,
                inputIcon = iconDismiss,
                buttonColor = TextWhite,
                onClick = { onDismissRequest() }
            )
        },
        containerColor = containerColor,
        iconContentColor = highlightColor,
        textContentColor = TextWhite

    )
}