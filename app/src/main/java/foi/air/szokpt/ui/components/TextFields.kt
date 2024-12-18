package foi.air.szokpt.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextGray

@Composable
fun LoginTextField(
    label: String,
    value: String,
    onValueChange: (newValue: String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    isPasswordField: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            unfocusedPlaceholderColor = Color.Gray,
            focusedTrailingIconColor = Primary,
            cursorColor = Primary,
            unfocusedBorderColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            focusedTextColor = Color.White

        ),
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {  // Correctly call onValueChange
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear input",
                        tint = TextGray
                    )
                }
            }
        }
    )
}

@Composable
fun InputTextField(
    label: String,
    value: String,
    onValueChange: (newValue: String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            unfocusedPlaceholderColor = Color.Gray,
            focusedTrailingIconColor = Primary,
            cursorColor = Primary,
            unfocusedBorderColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            focusedTextColor = Color.White

        ),
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {  // Correctly call onValueChange
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear input",
                        tint = TextGray
                    )
                }
            }
        }
    )
}