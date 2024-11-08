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
    FillMaxWidth,
    FillMaxHeight,
    FillMaxSize,
    WrapContent
}

val BackgroundColor = Brush.horizontalGradient(           // Lowest Level - Gradient
    colors = listOf(
        Color(0xFF101010),
        Color(0xFF222222)
    )
)

// Border radius of everything
val AppBorderRadius: Dp = 30.dp                           // Default radius across the app

// Drop shadow constants
val ShadowColor: Color = Color.Black.copy(alpha = 0.3f)  // Semitransparent black
val ShadowElevation: Dp = 4.dp                            // Shadow depth
val ShadowBlurRadius: Dp = 8.dp                           // Blur of the shadow