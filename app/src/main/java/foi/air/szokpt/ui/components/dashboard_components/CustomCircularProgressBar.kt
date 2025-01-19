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

/**
 * A custom circular progress bar consisting of two concentric [CircularProgressIndicator]s.
 * The lower one (background) is always at 100% to form a track, and the upper one represents
 * the actual [progress].
 *
 * @param progress The fraction of progress to display, expressed as a [Float] between 0.0 and 1.0.
 * @param modifier A [Modifier] for this composable; typically includes size, layout, or styling.
 * @param backgroundColor The color of the track (the behind-the-scenes, 100% [CircularProgressIndicator]).
 * @param progressColor The color of the actual progress arc.
 * @param strokeWidth The width of the circular stroke, in [Dp].
 */
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
