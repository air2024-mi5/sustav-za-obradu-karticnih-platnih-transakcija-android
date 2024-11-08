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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foi.air.szokpt.ui.theme.AppBorderRadius
import foi.air.szokpt.ui.theme.BGLevelOne
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
    tileSizeMode: TileSizeMode = TileSizeMode.WrapContent,
    innerPadding: Dp = 8.dp,
    outerMargin: Dp = 8.dp,
    minWidth: Dp = 200.dp,
    minHeight: Dp = 200.dp,
    color: Color = TileElevation.ONE.color as Color,
    content: @Composable BoxScope.() -> Unit
) {
    val sizeModifier = when (tileSizeMode) {
        TileSizeMode.FillMaxWidth -> Modifier.fillMaxWidth()
        TileSizeMode.FillMaxHeight -> Modifier.fillMaxHeight()
        TileSizeMode.FillMaxSize -> Modifier.fillMaxSize()
        TileSizeMode.WrapContent -> Modifier.wrapContentSize()
    }

    Box(
        modifier = sizeModifier
            .then(sizeModifier)
            .sizeIn(minWidth = minWidth, minHeight = minHeight)
            .padding(outerMargin)
            .clip(RoundedCornerShape(AppBorderRadius))
            .background(color)
            .padding(innerPadding),
        content = content
    )
}