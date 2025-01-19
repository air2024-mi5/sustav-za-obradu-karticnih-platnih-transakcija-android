package foi.air.szokpt.ui.components.interactible_components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.Primary


/**
 * A custom bouncing outlined button component that provides animated effects for scaling
 * the button, icon, and border stroke when pressed.
 *
 * @param modifier Modifier to be applied to the outer Box container
 * @param inputText Text to display on the button
 * @param inputIcon Icon to display beside the button text
 * @param contentColor Color of the button's content (text and icon)
 * @param borderColor Color of the button's border
 * @param onClick Action to perform when the button is clicked
 */
@Composable
fun OutlineBouncingButton(
    modifier: Modifier = Modifier,
    inputText: String = "Button",
    inputIcon: ImageVector = Icons.Filled.FavoriteBorder,
    contentColor: Color = Primary,
    borderColor: Color = Primary,
    fontSize: TextUnit = 16.sp,
    iconSize: Dp = 24.dp,
    padding: Dp = 8.dp,
    spacer: Dp = 4.dp,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 500f
        ),
        finishedListener = { isPressed = false }
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    val strokeScale by animateDpAsState(
        targetValue = if (isPressed) 3.dp else 1.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = {
                isPressed = true
                onClick() // From the argument. Passed onClick to execute
            },
            modifier = Modifier
                .scale(scale)
                .padding(padding),
            border = BorderStroke(strokeScale, borderColor)
        ) {
            Icon(
                imageVector = inputIcon,
                contentDescription = "Favorite Icon",
                tint = contentColor,
                modifier = Modifier
                    .size(iconSize)
                    .scale(iconScale)
            )
            Spacer(modifier = Modifier.width(spacer))
            Text(
                text = inputText,
                color = contentColor,
                fontSize = fontSize,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun PreviewOutlineBoucingButton() {
    OutlineBouncingButton { }
}