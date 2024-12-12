package foi.air.szokpt.ui.components.pagination_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary

@Composable
fun Pagination(
    currentPage: Int?,
    totalPages: Int?,
    onPageSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val buttons = mutableListOf<Int>()

        buttons.add(1)

        if (currentPage != null && totalPages != null) {
            if (currentPage - 1 > 1) {
                buttons.add(currentPage - 1)
            }

            if (currentPage > 1 && currentPage < totalPages) {
                buttons.add(currentPage)
            }

            if (currentPage + 1 < totalPages) {
                buttons.add(currentPage + 1)
            }

            if (totalPages > 1) {
                buttons.add(totalPages)
            }
        }

        buttons.distinct().sorted().forEach { page ->
            val isCurrent = page == currentPage

            Button(
                onClick = { if (!isCurrent) onPageSelected(page) },
                modifier = Modifier.padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCurrent) Primary else Secondary,
                    contentColor = if (isCurrent) Color.White else Color.White
                )
            ) {
                Text(text = page.toString())
            }
        }
    }
}
