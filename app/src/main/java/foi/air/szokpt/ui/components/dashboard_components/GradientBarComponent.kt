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