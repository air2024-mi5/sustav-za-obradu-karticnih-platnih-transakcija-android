package foi.air.szokpt.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.Primary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    maxDate: LocalDate = LocalDate.now(),
    initialDate: LocalDate?,
    label: String,
    maxWidth: Dp? = null
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val today = LocalDate.now()

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val date = LocalDate.of(year, month + 1, dayOfMonth)
            if (!date.isAfter(maxDate)) {
                selectedDate = date
                onDateSelected(date)
            }
        },
        today.year,
        today.monthValue - 1,
        today.dayOfMonth
    ).apply {
        datePicker.maxDate = calendar.timeInMillis
    }

    OutlinedTextField(
        value = selectedDate?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: "",
        onValueChange = {},
        readOnly = true,
        label = {
            Text(
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Pick a date",
                    tint = Primary
                )
            }
        },
        textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        modifier = modifier
            .then(if (maxWidth != null) Modifier.widthIn(max = maxWidth) else Modifier)
            .clickable { datePickerDialog.show() }
            .padding(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            unfocusedPlaceholderColor = Color.Gray,
            cursorColor = Primary,
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            focusedTrailingIconColor = Primary
        )
    )
}
