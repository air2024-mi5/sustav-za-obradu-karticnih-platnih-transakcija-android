package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    progressColor: Color = Color.White,
    strokeWidth: Dp = 8.dp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(110.dp)
    ) {
        CircularProgressIndicator(
            progress = {
                1f
            },
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
        )

        CircularProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
        )
    }
}
