package foi.air.szokpt.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextGray

@Composable
fun StyledTextField(
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
fun InputNumberField(
    label: String,
    value: String,
    onValueChange: (newValue: String) -> Unit,
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = false,
    width: Dp? = null
) {
    val keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    )

    val validatedValue = value.filter { it.isDigit() }

    OutlinedTextField(
        value = validatedValue,
        onValueChange = { newValue ->
            onValueChange(newValue.filter { it.isDigit() })
        },
        label = {
            Text(
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        singleLine = true,
        modifier = if (fillMaxSize) {
            modifier.fillMaxWidth()
        } else if (width != null) {
            modifier.width(width)
        } else {
            modifier
        },
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
            if (validatedValue.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
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