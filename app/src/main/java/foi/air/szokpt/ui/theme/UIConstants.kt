package foi.air.szokpt.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class TileElevation(val color: Any) {
    ZERO(Color(0xFF202020)),
    ONE(Color(0xFF303030)),
    TWO(Color(0xFF393939)),
    THREE(Color(0xFF454545))
}

enum class TileSizeMode {
    FILL_MAX_WIDTH,
    FILL_MAX_HEIGHT,
    FILL_MAX_SIZE,
    WRAP_CONTENT
}

val BackgroundColor = Brush.horizontalGradient(
    colors = listOf(
        BGLevelZeroLow,
        BGLevelZeroHigh
    )
)

val AppBorderRadius: Dp = 30.dp

val ShadowSpotColor: Color = Color.Black
val ShadowTileElevation: Dp = 15.dp

