package foi.air.szokpt.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class TileElevation(val color: Any) {
    ZERO(Color(0xFF202020)),                        // Level Zero
    ONE(Color(0xFF303030)),                         // Level One
    TWO(Color(0xFF393939)),                         // Level Two
    THREE(Color(0xFF454545))                        // Level Three
}

// SizeMode indicates the sizing fill of the Tile in a container
enum class TileSizeMode {
    FILL_MAX_WIDTH,
    FILL_MAX_HEIGHT,
    FILL_MAX_SIZE,
    WRAP_CONTENT
}

enum class TileSize(val rows: Float) {
    ONE_ROW(1.0f),
    ONE_AND_HALF(1.5f),
    TWO_ROWS(2.0f),
    THREE_ROW(3.0f)
}

// Background gradient of the app
val BackgroundColor = Brush.horizontalGradient(           // Lowest Level - Gradient
    colors = listOf(
        BGLevelZeroLow,
        BGLevelZeroHigh
    )
)

// Border radius of everything
val AppBorderRadius: Dp = 30.dp                           // Default radius across the app

// Drop shadow constants
val ShadowSpotColor: Color = Color.Black
val ShadowAmbientColor: Color = Color.LightGray
val ShadowTileElevation: Dp = 16.dp

