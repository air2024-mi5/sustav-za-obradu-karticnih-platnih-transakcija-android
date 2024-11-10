package foi.air.szokpt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foi.air.szokpt.ui.theme.AppBorderRadius
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.ShadowAmbientColor
import foi.air.szokpt.ui.theme.ShadowSpotColor
import foi.air.szokpt.ui.theme.ShadowTileElevation
import foi.air.szokpt.ui.theme.TileElevation
import foi.air.szokpt.ui.theme.TileSizeMode

/**
 * A composable function that creates a customizable tile with configurable size, padding, margin,
 * and color. The tile's size behavior can be controlled using the `tileSizeMode` parameter, allowing
 * it to fill available space or wrap content as needed.
 *
 * @param modifier The Modifier to be applied to the tile, allowing further customization from the
 *                  caller.
 * @param tileSizeMode Defines the size behavior of the tile (e.g., FillMaxWidth, FillMaxHeight,
 *                  FillMaxSize, WrapContent).
 * @param innerPadding The padding inside the tile, applied around the content.
 * @param outerMargin The margin around the outside of the tile, creating space between it and
 *                  other UI elements.
 * @param minWidth The minimum width of the tile, ensuring it won't be smaller than this value.
 * @param minHeight The minimum height of the tile, ensuring it won't be smaller than this value.
 * @param color The background color of the tile.
 * @param content The composable content displayed within the tile, aligned within the `Box`'s scope.
 */
@Composable
fun TileSegment(
    modifier: Modifier = Modifier,
    tileSizeMode: TileSizeMode = TileSizeMode.WRAP_CONTENT,
    innerPadding: Dp = 8.dp,
    outerMargin: Dp = 8.dp,
    minWidth: Dp = 200.dp,
    minHeight: Dp = 200.dp,
    color: Color = TileElevation.ONE.color as Color,
    content: @Composable BoxScope.() -> Unit
) {
    val sizeModifier = when (tileSizeMode) {
        TileSizeMode.FILL_MAX_WIDTH -> Modifier.fillMaxWidth()
        TileSizeMode.FILL_MAX_HEIGHT -> Modifier.fillMaxHeight()
        TileSizeMode.FILL_MAX_SIZE -> Modifier.fillMaxSize()
        TileSizeMode.WRAP_CONTENT -> Modifier.wrapContentSize()
    }

    Box(
        modifier = modifier
            .then(sizeModifier)
            .sizeIn(minWidth = minWidth, minHeight = minHeight)
            .padding(outerMargin)
            .shadow(
                elevation = ShadowTileElevation,
                shape = RoundedCornerShape(28.dp),
                //ambientColor = ShadowAmbientColor,
                spotColor = ShadowSpotColor
            )
            .clip(RoundedCornerShape(AppBorderRadius))
            .background(color)
            .padding(innerPadding),
        content = content
    )
}

@Preview
@Composable
fun TileSegmentPreview() {
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        minWidth = 200.dp,
        minHeight = 200.dp,
        color = BGLevelOne,
        content = {
            Text(
                text = "Test text 1234567890 abecdefghijklmn",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    )
}