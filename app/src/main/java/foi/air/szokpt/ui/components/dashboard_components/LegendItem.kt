package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import foi.air.szokpt.R

@Composable
fun LegendItem(label: String, value: Int, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        val icon = when (label.lowercase()) {
            "successful" -> ImageVector.vectorResource(id = R.drawable.memo_circle_check)
            "canceled" -> ImageVector.vectorResource(id = R.drawable.document_circle_wrong)
            "rejected" -> ImageVector.vectorResource(id = R.drawable.file_circle_info)
            else -> null
        }

        if (icon != null) {
            Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(16.dp))
        } else {
            Canvas(modifier = Modifier.size(10.dp)) {
                drawCircle(color = color)
            }
        }

        Spacer(modifier = Modifier.width(7.dp))
        Text(text = "$label: $value", style = MaterialTheme.typography.bodyMedium, color = color)
    }
}