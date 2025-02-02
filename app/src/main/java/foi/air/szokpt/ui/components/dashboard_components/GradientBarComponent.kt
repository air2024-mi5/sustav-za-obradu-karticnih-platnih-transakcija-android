package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary

/**
 * A customizable gradient bar component used for visualizing data in a bar chart.
 * @param modifier Modifier to be applied to the bar component
 * @param heightFraction Fractional height of the bar, relative to maxHeight (0.0 to 1.0)
 * @param maxHeight The maximum height of the bar in dp
 * @param colorStart The starting color of the vertical gradient
 * @param colorEnd The ending color of the vertical gradient
 * @param cornerRadius The radius of the rounded corners at the top of the bar
 */
@Composable
fun GradientBarComponent(
    modifier: Modifier = Modifier,
    heightFraction: Float,
    maxHeight: Int = 100,
    colorStart: Color = Primary,
    colorEnd: Color = Secondary,
    cornerRadius: Dp = 4.dp
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(colorStart, colorEnd)
    )

    Box(
        modifier = modifier
            .width(24.dp)
            .height((maxHeight * heightFraction).dp)
            .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
            .background(brush = gradientBrush)
    )
}