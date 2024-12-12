package foi.air.szokpt.ui.components.pagination_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary

@Composable
fun Pagination(
    currentPage: Int?,
    totalPages: Int?,
    onPageSelected: (Int) -> Unit
) {
    if (currentPage == null || totalPages == null || totalPages < 1) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val buttons = mutableListOf<Int>()

        buttons.add(1)

        if (currentPage > 3) {
            buttons.add(-1)
        }

        if (currentPage - 1 > 1) {
            buttons.add(currentPage - 1)
        }

        buttons.add(currentPage)

        if (currentPage + 1 < totalPages) {
            buttons.add(currentPage + 1)
        }

        if (currentPage < totalPages - 2) {
            buttons.add(-2)
        }

        buttons.add(totalPages)

        buttons.distinct().forEach { page ->
            if (page < 0) {
                Text(
                    text = "...",
                    color = Color.White,
                    fontSize = 12.sp
                )
            } else {
                val isCurrent = page == currentPage
                Button(
                    onClick = { if (!isCurrent) onPageSelected(page) },
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCurrent) Primary else Secondary,
                        contentColor = Color.White
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = page.toString(),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
